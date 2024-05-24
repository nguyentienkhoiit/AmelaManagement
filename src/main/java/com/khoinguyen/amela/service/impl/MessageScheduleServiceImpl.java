package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.entity.User;
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
import com.khoinguyen.amela.util.StringUtil;
import com.khoinguyen.amela.util.UserHelper;
import com.khoinguyen.amela.util.ValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

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
    ValidationService validationService;

    @Transactional
    @Override
    public void createMessages(MessageScheduleDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        if (request.getSenderName().isEmpty()) {
            request.setSenderName("Administrator");
        }

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
            if (request.getGroupId() != 0) {
                messageSchedule.setGroup(groupRepository.findById(request.getGroupId()).orElseThrow());
                messageSchedule.setUserMessageSchedules(null);
                messageScheduleRepository.save(messageSchedule);
            }
            else validationService.updateErrors("groupId", "Please choose a group", errors);
        } else {
            List<User> listUsers = request.getUsersIds().stream()
                    .map(id -> userRepository.findByIdAndActive(id).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();

            if (listUsers.isEmpty()) {
                validationService.updateErrors("usersIds", "Please choose at least one user", errors);
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
            messageScheduleRepository.save(messageSchedule);
        }
    }

    @Override
    public PagingDtoResponse<MessageScheduleDtoResponse> getAllMessagesAdmin(PagingDtoRequest pagingDtoRequest) {
        return messageScheduleCriteria.getAllMessages(pagingDtoRequest);
    }

    public MessageScheduleUpdateResponse setMessage(MessageScheduleUpdateResponse response) {
        User userLoggedIn = userHelper.getUserLogin();
        User user = userRepository.findById(userLoggedIn.getId()).orElseThrow();
        String messages = response.getMessage();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{{name}}", String.format("%s %s", userLoggedIn.getFirstname(), userLoggedIn.getLastname()));

        int age = Period.between(userLoggedIn.getDateOfBirth(), LocalDate.now()).getYears();
        placeholders.put("{{age}}", Integer.toString(age));

        placeholders.put("{{address}}", user.getAddress());
        placeholders.put("{{code}}", user.getCode());
        placeholders.put("{{email}}", user.getEmail());
        placeholders.put("{{phone}}", user.getPhone());
        placeholders.put("{{username}}", user.getUsername());
        placeholders.put("{{department}}", user.getDepartment().getName());
        placeholders.put("{{position}}", user.getJobPosition().getName());

        if (user.getRole().getName().equalsIgnoreCase(ADMIN_NAME)) {
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
            List<User> listUsers = request.getUsersIds().stream()
                    .map(id -> userRepository.findByIdAndActive(id).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();

            if (listUsers.isEmpty()) {
                validationService.updateErrors("usersIds", "Please choose at least one user", errors);
            }

            if (!errors.isEmpty()) return;

            messageSchedule = messageScheduleRepository.save(messageSchedule);

            List<Long> userIds = messageSchedule.getUserMessageSchedules()
                    .stream()
                    .map(u -> u.getUser().getId())
                    .toList();

            log.info("result: {}", request.getUsersIds().equals(userIds));
            if (!request.getUsersIds().equals(userIds)) {
                userMessageScheduleRepository.deleteByMessageScheduleId(messageSchedule.getId());
                List<UserMessageSchedule> userMessageScheduleList = new ArrayList<>();
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
        }
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
