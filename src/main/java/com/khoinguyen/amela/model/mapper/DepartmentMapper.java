package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.Department;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;

public class DepartmentMapper {
    public static DepartmentDtoResponse toDepartment(Department request) {
        return DepartmentDtoResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .status(request.isStatus())
                .createdAt(request.getCreatedAt())
                .updateAt(request.getUpdateAt())
                .createdBy(request.getCreatedBy())
                .updateBy(request.getUpdateBy())
                .build();
    }
}
