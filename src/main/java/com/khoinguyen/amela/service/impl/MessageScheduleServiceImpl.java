package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.entity.UserGroup;
import com.khoinguyen.amela.entity.UserMessageSchedule;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoRequest;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoResponse;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.mapper.MessageScheduleMapper;
import com.khoinguyen.amela.repository.GroupRepository;
import com.khoinguyen.amela.repository.MessageScheduleRepository;
import com.khoinguyen.amela.repository.UserMessageScheduleRepository;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.repository.criteria.MessageScheduleCriteria;
import com.khoinguyen.amela.service.MessageScheduleService;
import com.khoinguyen.amela.util.*;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import static com.khoinguyen.amela.util.Constant.ADMIN_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageScheduleServiceImpl implements MessageScheduleService {
    UserHelper userHelper;
    MessageScheduleRepository messageScheduleRepository;
    MessageScheduleCriteria messageScheduleCriteria;
    UserRepository userRepository;
    GroupRepository groupRepository;
    UserMessageScheduleRepository userMessageScheduleRepository;
    EmailHandler emailHandler;
    ThreadPoolTaskScheduler taskScheduler;
    Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    FileHelper fileHelper;
    ValidationService validationService;

    private List<User> getUsersFromSchedule(MessageSchedule messageSchedule) {
        if (messageSchedule.getGroup() != null) {
            return messageSchedule.getGroup().getUserGroups().stream()
                    .map(UserGroup::getUser)
                    .toList();
        } else if (messageSchedule.getUserMessageSchedules() != null) {
            return messageSchedule.getUserMessageSchedules().stream()
                    .map(UserMessageSchedule::getUser)
                    .toList();
        }
        return Collections.emptyList();
    }

    private void setSchedulePublishMessage(MessageSchedule messageSchedule) {
        // Cancel existing task if it exists
        ScheduledFuture<?> existingTask = scheduledTasks.get(messageSchedule.getId());
        if (existingTask != null) {
            existingTask.cancel(false);
        }

        // Schedule new task
        List<User> users = getUsersFromSchedule(messageSchedule);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(messageSchedule.getPublishAt(), ZoneId.systemDefault());
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(() -> {
            try {
                emailHandler.sendNotificationMessage(messageSchedule, users);
            } catch (MessagingException | UnsupportedEncodingException e) {
                log.error("Failed to send notification message: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to send notification message", e);
            }
        }, zonedDateTime.toInstant());

        // Save the new task in the map
        scheduledTasks.put(messageSchedule.getId(), scheduledFuture);
    }

    @Transactional
    @Override
    public void createMessages(MessageScheduleDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        if (request.getSenderName().isEmpty()) {
            request.setSenderName("Administrator");
        }

//        var listBannedWords = fileHelper.getListBannedWord(request.getMessage());
//        if (!listBannedWords.isEmpty()) {
//            validationService.updateErrors("message", "Messages contains invalid words like " + listBannedWords, errors);
//        }

        if (request.getPublishAt() != null && request.getPublishAt().isBefore(LocalDateTime.now())) {
            validationService.updateErrors("publishAt", "The publish at must be in the future", errors);
        }

        MessageSchedule messageSchedule = MessageSchedule.builder()
                .createdAt(LocalDateTime.now())
                .publishAt(request.getPublishAt())
                .createdBy(userLoggedIn.getId())
                .message(request.getMessage())
                .senderName(request.getSenderName())
                .status(true)
                .subject(request.getSubject())
                .viewers(0L)
                .updateAt(LocalDateTime.now())
                .updateBy(userLoggedIn.getId())
                .build();

        if (request.isChoice()) {
            if (request.getGroupId() == 0) {
                validationService.updateErrors("groupId", "Please choose a group", errors);
            } else {
                messageSchedule.setGroup(groupRepository.findById(request.getGroupId()).orElseThrow());
                messageSchedule.setUserMessageSchedules(null);
                messageSchedule = messageScheduleRepository.save(messageSchedule);
            }

            if (!errors.isEmpty()) return;
        } else {
            //cut email to list
            Set<String> listEmail = Arrays.stream(request.getListMail()
                            .split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> s.contains("@"))
                    .collect(Collectors.toSet());

            //check validate email exist in database
            StringBuilder messages = new StringBuilder();
            List<User> listUsers = new ArrayList<>();
            for (var email : listEmail) {
                var userOptional = userRepository
                        .findByEmail(email)
                        .filter(u -> !u.getRole().getName().equals(ADMIN_NAME));
                if (userOptional.isEmpty()) {
                    messages.append(email).append(", ");
                } else listUsers.add(userOptional.get());
            }

            //list of mail is not exist
            if (!messages.isEmpty()) {
                String msg = messages.toString().trim().substring(0, messages.length() - 2).concat(" is not existed");
                validationService.updateErrors("listMail", msg, errors);
            }

            if (listUsers.isEmpty()) {
                validationService.updateErrors("listMail", "Please input at least one email", errors);
            }

            if (!errors.isEmpty()) return;

            messageSchedule.setGroup(null);
            messageSchedule = messageScheduleRepository.save(messageSchedule);

            Set<UserMessageSchedule> userMessageScheduleList = new HashSet<>();
            for (var user : listUsers) {
                UserMessageSchedule userMessageSchedule = UserMessageSchedule.builder()
                        .messageSchedule(messageSchedule)
                        .user(user)
                        .build();
                userMessageScheduleList.add(userMessageSchedule);
            }
            var ums = userMessageScheduleRepository.saveAll(userMessageScheduleList);
            messageSchedule.setUserMessageSchedules(ums);
            messageSchedule = messageScheduleRepository.save(messageSchedule);
        }
        setSchedulePublishMessage(messageSchedule);
    }

    @Override
    public PagingDtoResponse<MessageScheduleDtoResponse> getAllMessagesAdmin(PagingDtoRequest pagingDtoRequest) {
        return messageScheduleCriteria.getAllMessages(pagingDtoRequest);
    }

    public MessageScheduleUpdateResponse setMessage(MessageScheduleUpdateResponse response) {
        User userLoggedIn = userHelper.getUserLogin();
        String messages = response.getMessage();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{{name}}", String.format("%s %s", userLoggedIn.getFirstname(), userLoggedIn.getLastname()));

        int age = Period.between(userLoggedIn.getDateOfBirth(), LocalDate.now()).getYears();
        placeholders.put("{{age}}", Integer.toString(age));

        placeholders.put("{{address}}", userLoggedIn.getAddress());
        placeholders.put("{{code}}", userLoggedIn.getCode());
        placeholders.put("{{email}}", userLoggedIn.getEmail());
        placeholders.put("{{phone}}", userLoggedIn.getPhone());
        placeholders.put("{{username}}", userLoggedIn.getUsername());
        placeholders.put("{{department}}", userLoggedIn.getDepartment().getName());
        placeholders.put("{{position}}", userLoggedIn.getJobPosition().getName());

        if (userLoggedIn.getRole().getName().equalsIgnoreCase(ADMIN_NAME)) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                entry.setValue(entry.getKey());
            }
        }

        response.setMessage(replacePlaceholders(messages, placeholders));
        return response;
    }

    private String replacePlaceholders(String message, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = entry.getKey();
            String value = entry.getValue() != null ? entry.getValue() : "";
            message = message.replace(placeholder, value);
        }
        return message;
    }

    @Transactional
    @Override
    public MessageScheduleUpdateResponse getByMessageScheduleId(Long id, String type) {
        return messageScheduleRepository.findById(id)
                .map(messageSchedule -> {
                    if ("detail".equalsIgnoreCase(type)) {
                        messageSchedule.setViewers(messageSchedule.getViewers() + 1);
                        return messageScheduleRepository.save(messageSchedule);
                    } else {
                        return messageSchedule;
                    }
                })
                .map(messageSchedule -> {
                    MessageScheduleUpdateResponse response = MessageScheduleMapper.toMessageScheduleUpdateResponse(messageSchedule);
                    if ("detail".equalsIgnoreCase(type)) {
                        return setMessage(response);
                    } else {
                        return response;
                    }
                })
                .orElse(null);
    }

    @Transactional
    @Override
    public void updateMessages(MessageScheduleDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        //default sender if null
        if (request.getSenderName().isEmpty()) {
            request.setSenderName("Administrator");
        }

        //check message schedule exist
        MessageSchedule messageSchedule = messageScheduleRepository.findById(request.getId()).orElse(null);
        if (messageSchedule == null) {
            validationService.updateErrors("message", "Message is not found", errors);
        }

        Set<String> messageInvalid = StringUtil.extractAttributeNameInvalid(request.getMessage());
        if (!messageInvalid.isEmpty()) {
            validationService.updateErrors("message", messageInvalid + " is invalid attribute", errors);
        }

        //update message schedule
        assert messageSchedule != null;
        messageSchedule.setMessage(request.getMessage());
        messageSchedule.setPublishAt(request.getPublishAt());
        messageSchedule.setSubject(request.getSubject());
        messageSchedule.setUpdateAt(LocalDateTime.now());
        messageSchedule.setSenderName(request.getSenderName());
        messageSchedule.setUpdateBy(userLoggedIn.getId());


        //consist changing
        if (request.isChoice()) {
            if (request.getGroupId() == 0) {
                validationService.updateErrors("groupId", "Please choose a group", errors);
            } else {
                messageSchedule.setGroup(groupRepository.findById(request.getGroupId()).orElseThrow());
                messageSchedule = messageScheduleRepository.save(messageSchedule);
            }

            if (!errors.isEmpty()) return;
        } else {
            //cut email to list
            Set<String> listEmail = Arrays.stream(request.getListMail()
                            .split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> s.contains("@"))
                    .collect(Collectors.toSet());

            //check validate email exist in database
            StringBuilder messages = new StringBuilder();
            List<User> listUsers = new ArrayList<>();
            for (var email : listEmail) {
                var userOptional = userRepository.findByEmail(email);
                if (userOptional.isEmpty()) {
                    messages.append(email).append(", ");
                } else listUsers.add(userOptional.get());
            }

            //list of mail is not exist
            if (!messages.isEmpty()) {
                String msg = messages.toString().trim().substring(0, messages.length() - 2).concat(" is not existed");
                validationService.updateErrors("listMail", msg, errors);
            }

            if (listUsers.isEmpty()) {
                validationService.updateErrors("listMail", "Please input at least one email", errors);
            }

            if (!errors.isEmpty()) return;

            messageSchedule = messageScheduleRepository.save(messageSchedule);

            if (!MessageScheduleMapper.getListMailString(messageSchedule).equalsIgnoreCase(listEmail.toString())) {
                userMessageScheduleRepository.deleteAll(userMessageScheduleRepository.findByMessageScheduleId(messageSchedule.getId()));
            }

            Set<UserMessageSchedule> userMessageScheduleList = new HashSet<>();
            for (var user : listUsers) {
                UserMessageSchedule userMessageSchedule = UserMessageSchedule.builder()
                        .messageSchedule(messageSchedule)
                        .user(user)
                        .build();
                userMessageScheduleList.add(userMessageSchedule);
            }
            var ums = userMessageScheduleRepository.saveAll(userMessageScheduleList);
            messageSchedule.setUserMessageSchedules(ums);
            messageSchedule = messageScheduleRepository.save(messageSchedule);
        }
        setSchedulePublishMessage(messageSchedule);
    }

    @Override
    public PagingDtoResponse<MessageScheduleDtoResponse> getAllMessagesUser(PagingDtoRequest pagingDtoRequest) {
        return messageScheduleCriteria.getAllMessages(pagingDtoRequest);
    }

    @Override
    public boolean changeStatus(Long id) {
        User userLoggedIn = userHelper.getUserLogin();

        var messageScheduleOptional = messageScheduleRepository.findById(id);
        if (messageScheduleOptional.isPresent()) {
            var messageSchedule = messageScheduleOptional.get();
            //delete schedule
            if (messageSchedule.isStatus()) {
                ScheduledFuture<?> existingTask = scheduledTasks.get(messageSchedule.getId());
                if (existingTask != null) {
                    existingTask.cancel(false);
                }
            }
            //open schedule
            else setSchedulePublishMessage(messageSchedule);

            messageSchedule.setUpdateAt(LocalDateTime.now());
            messageSchedule.setUpdateBy(userLoggedIn.getId());
            messageSchedule.setStatus(!messageSchedule.isStatus());
            messageScheduleRepository.save(messageSchedule);

            return true;
        }
        return false;
    }

    @Override
    public List<MessageScheduleDtoResponse> getTopMessagesScheduleForUser(Long topElement, Long id) {
        PagingDtoRequest pagingDtoRequest = PagingDtoRequest.builder()
                .pageIndex("1")
                .pageSize(topElement.toString())
                .build();
        return messageScheduleCriteria
                .getAllMessages(pagingDtoRequest).data()
                .stream()
                .filter(response -> !response.getId().equals(id))
                .toList();
    }

    @Transactional
    @Override
    public MessageScheduleDtoRequest getMessageRequestById(Long messageId) {
        var mes = getByMessageScheduleId(messageId, "");
        mes.setPublishAt(LocalDateTime.now().plusMinutes(1L));
        return MessageScheduleMapper.toMessageScheduleDtoRequest(mes);
    }
}
