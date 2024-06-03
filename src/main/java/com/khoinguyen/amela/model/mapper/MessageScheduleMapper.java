package com.khoinguyen.amela.model.mapper;

import java.time.LocalDateTime;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoRequest;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoResponse;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleUpdateResponse;
import com.khoinguyen.amela.util.DateTimeHelper;

public class MessageScheduleMapper {
    public static MessageScheduleDtoResponse toMessageScheduleDtoResponse(MessageSchedule request) {
        return MessageScheduleDtoResponse.builder()
                .id(request.getId())
                .publishAt(DateTimeHelper.formatLocalDateTimeFullText(request.getPublishAt()))
                .message(request.getMessage())
                .createdAt(DateTimeHelper.formatLocalDateTime(request.getCreatedAt()))
                .status(request.isStatus())
                .choice(request.getGroup() != null)
                .subject(request.getSubject())
                .viewers(request.getViewers())
                .isPublished(request.getPublishAt().isBefore(LocalDateTime.now()))
                .createdBy(request.getCreatedBy())
                .senderName(request.getSenderName())
                .updateAt(DateTimeHelper.formatLocalDateTimeFullText(request.getUpdateAt()))
                .updateBy(request.getUpdateBy())
                .build();
    }

    public static String getListMailString(MessageSchedule request) {
        StringBuilder listMail = new StringBuilder();
        if (!request.getUserMessageSchedules().isEmpty()) {
            for (var ums : request.getUserMessageSchedules()) {
                listMail.append(ums.getUser().getEmail()).append(", ");
            }
            listMail =
                    new StringBuilder(listMail.substring(0, listMail.toString().length() - 2));
        }
        return listMail.toString();
    }

    public static MessageScheduleUpdateResponse toMessageScheduleUpdateResponse(MessageSchedule request) {
        var usersIds = request.getUserMessageSchedules().stream()
                .map(m -> m.getUser().getId())
                .toList();

        return MessageScheduleUpdateResponse.builder()
                .id(request.getId())
                .publishAt(request.getPublishAt())
                .message(request.getMessage())
                .subject(request.getSubject())
                .senderName(request.getSenderName())
                .isPublished(request.getPublishAt().isBefore(LocalDateTime.now()))
                .groupId(request.getGroup() != null ? request.getGroup().getId() : null)
                .choice(request.getGroup() != null)
                .usersIds(usersIds)
                .build();
    }

    public static MessageScheduleUpdateResponse toMessageScheduleUpdateResponse(MessageScheduleDtoRequest request) {
        boolean isPublished =
                request.getPublishAt() == null || request.getPublishAt().isBefore(LocalDateTime.now());
        return MessageScheduleUpdateResponse.builder()
                .id(request.getId())
                .publishAt(request.getPublishAt())
                .message(request.getMessage())
                .subject(request.getSubject())
                .senderName(request.getSenderName())
                .isPublished(isPublished)
                .groupId(request.getGroupId())
                .choice(request.getUsersIds() == null)
                .usersIds(request.getUsersIds())
                .build();
    }

    public static MessageScheduleDtoRequest toMessageScheduleDtoRequest(MessageScheduleUpdateResponse response) {
        return MessageScheduleDtoRequest.builder()
                .id(response.getId())
                .publishAt(response.getPublishAt())
                .message(response.getMessage())
                .subject(response.getSubject())
                .senderName(response.getSenderName())
                .groupId(response.getGroupId())
                .usersIds(response.getUsersIds())
                .choice(response.isChoice())
                .build();
    }
}
