package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.repository.MessageScheduleRepository;
import com.khoinguyen.amela.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionMessages {
    UserHelper userHelper;
    MessageScheduleRepository messageScheduleRepository;
    UserRepository userRepository;

    public boolean checkPermission(Long id) {
        MessageSchedule messageSchedule = messageScheduleRepository.findById(id).orElse(null);
        if (messageSchedule == null) return false;


        //là admin
        User userLoggedIn = userHelper.getUserLogin();
        User user = userRepository.findById(userLoggedIn.getId()).orElseThrow();
        if (Constant.ADMIN_NAME.equalsIgnoreCase(userLoggedIn.getRole().getName())) {
            return true;
        }

        //chưa đến hạn
        if (messageSchedule.getPublishAt().isAfter(LocalDateTime.now())) return false;

        //xem choose group hay choose email
        boolean isInGroup = messageSchedule.getGroup() != null;

        if (isInGroup) {
            return messageSchedule.getGroup().getUserGroups()
                    .stream()
                    .anyMatch(userGroup -> userGroup.getUser().equals(user));
        } else {
            return messageSchedule.getUserMessageSchedules()
                    .stream()
                    .anyMatch(userMessageSchedule -> Objects.equals(userMessageSchedule.getUser().getId(), user.getId()));
        }
    }
}
