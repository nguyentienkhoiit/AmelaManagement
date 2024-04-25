package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.Department;
import com.khoinguyen.amela.entity.JobPosition;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;

public class JobPositionMapper {
    public static JobPositionDtoResponse toJobPosition(JobPosition request) {
        return JobPositionDtoResponse.builder()
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
