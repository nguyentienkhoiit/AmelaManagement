package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.Attendance;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoRequest;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoUpdateResponse;
import com.khoinguyen.amela.util.Constant;
import com.khoinguyen.amela.util.DateTimeHelper;

import java.time.Duration;

public class AttendanceMapper {
    public static AttendanceDtoResponse toAttendanceDtoResponse(Attendance request) {
        String checkoutTime = request.getCheckOutTime() != null ?
                DateTimeHelper.formatLocalTime(request.getCheckOutTime()) : "00:00:00";
        String workTime = "00:00";
        if (request.getCheckOutTime() != null) {
            Duration duration = Duration.between(request.getCheckInTime(), request.getCheckOutTime());
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            workTime = (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes);
        }
        User user = request.getUser();
        String fullName = String.format("%s %s", user.getFirstname(), user.getLastname());
        return AttendanceDtoResponse.builder()
                .id(request.getId())
                .status(request.isStatus())
                .user(UserMapper.toUserDtoResponse(request.getUser()))
                .checkOutTime(checkoutTime)
                .createdBy(request.getCreatedBy())
                .checkInTime(DateTimeHelper.formatLocalTime(request.getCheckInTime()))
                .updateBy(request.getUpdateBy())
                .updateAt(request.getUpdateAt())
                .day(DateTimeHelper.toDayOfWeek(request.getCheckDay()))
                .checkDay(DateTimeHelper.formatDate(request.getCheckDay(), "dd/MM/yyyy"))
                .workTime(workTime)
                .fullName(fullName)
                .isExpired(DateTimeHelper.isExpiredDay(request.getCheckDay(), Constant.IN_DAY_EDITED))
                .note(request.getNote())
                .build();
    }

    public static AttendanceDtoUpdateResponse toAttendanceDtoUpdateResponse(Attendance request) {
        return AttendanceDtoUpdateResponse.builder()
                .attendanceId(request.getId())
                .userId(request.getUser().getId())
                .checkDay(request.getCheckDay())
                .checkInTime(request.getCheckInTime())
                .checkOutTime(request.getCheckOutTime())
                .isExpired(DateTimeHelper.isExpiredDay(request.getCheckDay(), 3))
                .note(request.getNote())
                .build();
    }

    public static AttendanceDtoUpdateResponse toAttendanceDtoUpdateResponse(AttendanceDtoRequest request) {
        return AttendanceDtoUpdateResponse.builder()
                .checkDay(request.getCheckDay())
                .attendanceId(request.getAttendanceId())
                .userId(request.getUserId())
                .checkInTime(request.getCheckInTime())
                .checkOutTime(request.getCheckOutTime())
                .isExpired(DateTimeHelper.isExpiredDay(request.getCheckDay(), 3))
                .note(request.getNote())
                .build();
    }
}
