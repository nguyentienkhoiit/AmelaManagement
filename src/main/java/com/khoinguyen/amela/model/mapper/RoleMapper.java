package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.JobPosition;
import com.khoinguyen.amela.entity.Role;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.dto.role.RoleDtoResponse;

public class RoleMapper {
    public static RoleDtoResponse toRole(Role request) {
        return RoleDtoResponse.builder()
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
