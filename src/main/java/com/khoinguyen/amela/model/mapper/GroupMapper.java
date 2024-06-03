package com.khoinguyen.amela.model.mapper;

import java.util.List;

import com.khoinguyen.amela.entity.Group;
import com.khoinguyen.amela.model.dto.group.GroupDtoRequest;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.util.DateTimeHelper;

public class GroupMapper {
    public static GroupDtoResponse toGroupDtoResponse(Group request) {
        long count = request.getUserGroups().stream()
                .filter(x -> x.getUser().isActivated() && x.getUser().isEnabled())
                .count();
        List<Long> userIds =
                request.getUserGroups().stream().map(e -> e.getUser().getId()).toList();
        return GroupDtoResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .usersIds(userIds)
                .createdAt(DateTimeHelper.formatLocalDateTimeFullText(request.getCreatedAt()))
                .updateAt(DateTimeHelper.formatLocalDateTimeFullText(request.getUpdateAt()))
                .status(request.isStatus())
                .member(count)
                .build();
    }

    public static GroupDtoResponse toGroupDtoResponse(GroupDtoRequest request) {
        return GroupDtoResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .usersIds(request.getUsersIds())
                .build();
    }
}
