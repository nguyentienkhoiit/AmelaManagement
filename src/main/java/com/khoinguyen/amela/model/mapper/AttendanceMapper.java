package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.Attendance;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.util.DateTimeHelper;

public class AttendanceMapper {
    public static AttendanceDtoResponse toAttendanceDtoResponse(Attendance request) {
        return AttendanceDtoResponse.builder()
                .id(request.getId())
                .status(request.isStatus())
                .user(UserMapper.toUserDtoResponse(request.getUser()))
                .checkOutTime(DateTimeHelper.formatLocalDateTime(request.getCheckOutTime()))
                .createdAt(request.getCreatedAt())
                .createdBy(request.getCreatedBy())
                .checkInTime(DateTimeHelper.formatLocalDateTime(request.getCheckInTime()))
                .updateBy(request.getUpdateBy())
                .updateAt(request.getUpdateAt())
                .checkDay(DateTimeHelper.formatDate(request.getCheckDay()))
                .build();
    }
}
