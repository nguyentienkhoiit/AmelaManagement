package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.entity.User;
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
import com.khoinguyen.amela.util.UserHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        return messageScheduleCriteria.getAllMessagesAdmin(pagingDtoRequest);
    }

    @Override
    public MessageScheduleUpdateResponse getByMessageScheduleId(Long id) {
        Optional<MessageSchedule> messageScheduleOptional = messageScheduleRepository.findById(id);
        return messageScheduleOptional.map(MessageScheduleMapper::toMessageScheduleUpdateResponse).orElse(null);
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

            if(!MessageScheduleMapper.getListMailString(messageSchedule).equalsIgnoreCase(listEmail.toString())) {
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
}
