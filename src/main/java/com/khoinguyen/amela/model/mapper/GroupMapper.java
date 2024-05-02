package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.Group;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.util.DateTimeHelper;

public class GroupMapper {
    public static GroupDtoResponse toGroupDtoResponse(Group group) {
        String listMail = group.getUserGroups().stream()
                .map(e -> e.getUser().getEmail())
                .toList()
                .toString();
        return GroupDtoResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .listMail(listMail.substring(1, listMail.length() - 1))
                .createdAt(DateTimeHelper.formatLocalDateTimeFullText(group.getCreatedAt()))
                .status(group.isStatus())
                .build();
    }
}
