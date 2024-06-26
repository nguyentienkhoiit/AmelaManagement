package com.khoinguyen.amela.util;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.repository.MessageScheduleRepository;
import com.khoinguyen.amela.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionResources {
    UserHelper userHelper;
    MessageScheduleRepository messageScheduleRepository;
    UserRepository userRepository;

    public boolean checkPermission(Long id) {
        MessageSchedule messageSchedule = new MessageSchedule();

        // is admin
        User userLoggedIn = userHelper.getUserLogin();
        User user = userRepository.findById(userLoggedIn.getId()).orElseThrow();
        if (Constant.ADMIN_NAME.equalsIgnoreCase(userLoggedIn.getRole().getName())) {
            return true;
        } else {
            messageSchedule =
                    messageScheduleRepository.findByIdAndStatusTrue(id).orElse(null);
        }

        if (messageSchedule == null) return false;

        // chưa đến hạn
        if (messageSchedule.getPublishAt().isAfter(LocalDateTime.now())) return false;

        // xem choose group hay choose email
        boolean isInGroup = messageSchedule.getGroup() != null;

        if (isInGroup) {
            return messageSchedule.getGroup().getUserGroups().stream()
                    .anyMatch(userGroup -> userGroup.getUser().equals(user));
        } else {
            return messageSchedule.getUserMessageSchedules().stream()
                    .anyMatch(userMessageSchedule ->
                            Objects.equals(userMessageSchedule.getUser().getId(), user.getId()));
        }
    }

    public User checkPermissionExport(Long userId) {
        User userLoggedIn = userHelper.getUserLogin();
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        String roleName = userLoggedIn.getRole().getName();

        if (Constant.ADMIN_NAME.equalsIgnoreCase(roleName)
                || (userLoggedIn.getId().equals(user.getId()) && Constant.USER_NAME.equals(roleName))) {
            return user;
        }

        return null;
    }
}
