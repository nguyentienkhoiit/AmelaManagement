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
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.mapper.MessageScheduleMapper;
import com.khoinguyen.amela.repository.GroupRepository;
import com.khoinguyen.amela.repository.MessageScheduleRepository;
import com.khoinguyen.amela.repository.UserMessageScheduleRepository;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.repository.criteria.MessageScheduleCriteria;
import com.khoinguyen.amela.service.MessageScheduleService;
import com.khoinguyen.amela.util.EmailHandler;
import com.khoinguyen.amela.util.StringUtil;
import com.khoinguyen.amela.util.UserHelper;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

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

    @Scheduled(fixedDelay = 1000 * 120)
    public void checkPublishTime() {
        List<MessageSchedule> messageSchedules = messageScheduleRepository.findByPublishAtBeforeNow();
//        List<MessageSchedule> messageSchedules = messageScheduleRepository.findByGroupId(3L);
        if (messageSchedules.isEmpty()) return;

        Map<MessageSchedule, List<User>> reminderMailMap = new HashMap<>();
        for (MessageSchedule messageSchedule : messageSchedules) {
            if (messageSchedule.getGroup() != null) {
                List<UserGroup> userGroups = messageSchedule.getGroup().getUserGroups();
                List<User> users = userGroups.stream().map(UserGroup::getUser).toList();
                reminderMailMap.put(messageSchedule, users);
            } else if (messageSchedule.getUserMessageSchedules() != null) {
                List<UserMessageSchedule> userMessageSchedules = messageSchedule.getUserMessageSchedules();
                List<User> users = userMessageSchedules.stream().map(UserMessageSchedule::getUser).toList();
                reminderMailMap.put(messageSchedule, users);
            }
        }

        for (Map.Entry<MessageSchedule, List<User>> entry : reminderMailMap.entrySet()) {
            MessageSchedule messageSchedule = entry.getKey();
            List<User> users = entry.getValue();
            try {
                emailHandler.sendNotificationMessage(messageSchedule, users);
            } catch (MessagingException | UnsupportedEncodingException e) {
                return;
            }
        }

        log.info("Log time: {}", LocalDateTime.now());
    }

    @Override
    public ServiceResponse<String> createMessages(MessageScheduleDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        if (request.getSenderName().isEmpty()) {
            request.setSenderName("Administrator");
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
                response = new ServiceResponse<>(false, "groupId", "Please choose a group");
            } else {
                messageSchedule.setGroup(groupRepository.findById(request.getGroupId()).orElseThrow());
                messageSchedule.setUserMessageSchedules(null);
                messageScheduleRepository.save(messageSchedule);
            }
        } else {
            //cut email to list
            Set<String> listEmail = Arrays.stream(request.getListMail()
                            .split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> s.contains("@"))
                    .collect(Collectors.toSet());

            if (listEmail.isEmpty()) {
                response = new ServiceResponse<>(false, "listMail", "Please input at least one email");
            }

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
                response = new ServiceResponse<>(false, "listMail", msg);
                return response;
            }

            if (listUsers.isEmpty()) {
                response = new ServiceResponse<>(false, "listMail", "Please input at least one email");
            }

            messageSchedule.setGroup(null);
            MessageSchedule messageScheduleResult = messageScheduleRepository.save(messageSchedule);

            Set<UserMessageSchedule> userMessageScheduleList = new HashSet<>();
            for (var user : listUsers) {
                UserMessageSchedule userMessageSchedule = UserMessageSchedule.builder()
                        .messageSchedule(messageScheduleResult)
                        .user(user)
                        .build();
                userMessageScheduleList.add(userMessageSchedule);
            }
            userMessageScheduleRepository.saveAll(userMessageScheduleList);
        }
        return response;
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

        if (userLoggedIn.getRole().getName().equalsIgnoreCase("ADMIN")) {
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

    @Override
    public ServiceResponse<String> updateMessages(MessageScheduleDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        //default sender if null
        if (request.getSenderName().isEmpty()) {
            request.setSenderName("Administrator");
        }

        //check message schedule exist
        MessageSchedule messageSchedule = messageScheduleRepository.findById(request.getId()).orElse(null);
        if (messageSchedule == null) {
            response = new ServiceResponse<>(false, "message", "Message is not found");
            return response;
        }

        Set<String> messageInvalid = StringUtil.extractAttributeNameInvalid(request.getMessage());
        if (!messageInvalid.isEmpty()) {
            response = new ServiceResponse<>(false, "message", messageInvalid + " is invalid attribute");
            return response;
        }

        //update message schedule
        messageSchedule.setMessage(request.getMessage());
        messageSchedule.setPublishAt(request.getPublishAt());
        messageSchedule.setSubject(request.getSubject());
        messageSchedule.setUpdateAt(LocalDateTime.now());
        messageSchedule.setSenderName(request.getSenderName());
        messageSchedule.setUpdateBy(userLoggedIn.getId());

        //consist changing
        if (request.isChoice()) {
            if (request.getGroupId() == 0) {
                response = new ServiceResponse<>(false, "groupId", "Please choose a group");
            } else {
                messageSchedule.setGroup(groupRepository.findById(request.getGroupId()).orElseThrow());
                messageScheduleRepository.save(messageSchedule);
            }
        } else {
            //cut email to list
            Set<String> listEmail = Arrays.stream(request.getListMail()
                            .split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> s.contains("@"))
                    .collect(Collectors.toSet());

            if (listEmail.isEmpty()) {
                response = new ServiceResponse<>(false, "listMail", "Please input at least one email");
            }

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
                response = new ServiceResponse<>(false, "listMail", msg);
                return response;
            }

            if (listUsers.isEmpty()) {
                response = new ServiceResponse<>(false, "listMail", "Please input at least one email");
            }

            MessageSchedule messageScheduleResult = messageScheduleRepository.save(messageSchedule);

            if (!MessageScheduleMapper.getListMailString(messageSchedule).equalsIgnoreCase(listEmail.toString())) {
                userMessageScheduleRepository.deleteAll(userMessageScheduleRepository.findByMessageScheduleId(messageSchedule.getId()));
            }


            Set<UserMessageSchedule> userMessageScheduleList = new HashSet<>();
            for (var user : listUsers) {
                UserMessageSchedule userMessageSchedule = UserMessageSchedule.builder()
                        .messageSchedule(messageScheduleResult)
                        .user(user)
                        .build();
                userMessageScheduleList.add(userMessageSchedule);
            }
            userMessageScheduleRepository.saveAll(userMessageScheduleList);
        }
        return response;
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
                .collect(Collectors.toList());
    }
}
