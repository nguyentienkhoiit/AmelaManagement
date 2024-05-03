package com.khoinguyen.amela.model.dto.attendance;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceDtoRequest {
    Long attendanceId;

    @NotNull(message = "Check in time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime checkInTime;

    @NotNull(message = "Check out time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime checkOutTime;
    Long userId;
    String note;

    public LocalDateTime getCheckInTime() {
        return checkInTime == null ? LocalDateTime.now() : checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime == null ? LocalDateTime.now() : checkOutTime;
    }
}
