package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.repository.MessageScheduleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionMessages {
    UserHelper userHelper;
    MessageScheduleRepository messageScheduleRepository;

    public boolean checkPermission(Long id) {
        MessageSchedule messageSchedule = messageScheduleRepository.findById(id).orElse(null);
        if (messageSchedule == null) {
            return false;
        }

        User userLoggedIn = userHelper.getUserLogin();
        String userRole = userLoggedIn.getRole().getName();

        if (Constant.ADMIN_NAME.equalsIgnoreCase(userRole)) {
            return true;
        }

        boolean isInGroup = messageSchedule.getGroup() != null;

        if (isInGroup) {
            return messageSchedule.getGroup().getUserGroups()
                    .stream()
                    .anyMatch(userGroup -> userGroup.getUser().equals(userLoggedIn));
        } else {
            return messageSchedule.getUserMessageSchedules()
                    .stream()
                    .anyMatch(userMessageSchedule -> userMessageSchedule.getUser().equals(userLoggedIn));
        }
    }
}
