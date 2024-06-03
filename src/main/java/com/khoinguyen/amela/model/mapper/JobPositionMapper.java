package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.JobPosition;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.util.DateTimeHelper;

public class JobPositionMapper {
    public static JobPositionDtoResponse toJobPositionDtoResponse(JobPosition request) {
        long count = request.getUsers().stream()
                .filter(x -> x.isActivated() && x.isEnabled())
                .count();
        return JobPositionDtoResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .status(request.isStatus())
                .createdAt(DateTimeHelper.formatLocalDateTimeFullText(request.getCreatedAt()))
                .updateAt(DateTimeHelper.formatLocalDateTimeFullText(request.getUpdateAt()))
                .createdBy(request.getCreatedBy())
                .updateBy(request.getUpdateBy())
                .member(count)
                .build();
    }

    public static JobPositionDtoResponse toJobPositionDtoResponse(JobPositionDtoRequest request) {
        return JobPositionDtoResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
