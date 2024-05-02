package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.Attendance;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.util.DateTimeHelper;

public class AttendanceMapper {
    public static AttendanceDtoResponse toAttendanceDtoResponse(Attendance request) {
        String checkoutTime = request.getCheckOutTime() != null ?
                DateTimeHelper.formatLocalDateTime(request.getCheckOutTime()) : "";
        return AttendanceDtoResponse.builder()
                .id(request.getId())
                .status(request.isStatus())
                .user(UserMapper.toUserDtoResponse(request.getUser()))
                .checkOutTime(checkoutTime)
                .createdBy(request.getCreatedBy())
                .checkInTime(DateTimeHelper.formatLocalDateTime(request.getCheckInTime()))
                .updateBy(request.getUpdateBy())
                .updateAt(request.getUpdateAt())
                .checkDay(DateTimeHelper.formatDate(request.getCheckDay()))
                .build();
    }
}
