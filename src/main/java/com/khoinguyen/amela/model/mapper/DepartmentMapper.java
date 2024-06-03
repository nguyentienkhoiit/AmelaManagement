package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.Department;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoRequest;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.util.DateTimeHelper;

public class DepartmentMapper {
    public static DepartmentDtoResponse toDepartmentDtoResponse(Department request) {
        long count = request.getUsers().stream()
                .filter(x -> x.isActivated() && x.isEnabled())
                .count();
        return DepartmentDtoResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .status(request.isStatus())
                .createdAt(DateTimeHelper.formatLocalDateTimeFullText(request.getCreatedAt()))
                .updateAt(DateTimeHelper.formatLocalDateTimeFullText(request.getUpdateAt()))
                .createdBy(request.getCreatedBy())
                .member(count)
                .updateBy(request.getUpdateBy())
                .build();
    }

    public static DepartmentDtoResponse toDepartmentDtoResponse(DepartmentDtoRequest request) {
        return DepartmentDtoResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
