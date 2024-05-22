package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.Group;
import com.khoinguyen.amela.model.dto.group.GroupDtoRequest;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.util.DateTimeHelper;

public class GroupMapper {
    public static GroupDtoResponse toGroupDtoResponse(Group request) {
        long count = request.getUserGroups()
                .stream()
                .filter(x -> x.getUser().isActivated() && x.getUser().isEnabled())
                .count();
        String listMail = request.getUserGroups().stream()
                .map(e -> e.getUser().getEmail())
                .toList()
                .toString();
        return GroupDtoResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .listMail(listMail.substring(1, listMail.length() - 1))
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
                .listMail(request.getListMail())
                .build();
    }
}
