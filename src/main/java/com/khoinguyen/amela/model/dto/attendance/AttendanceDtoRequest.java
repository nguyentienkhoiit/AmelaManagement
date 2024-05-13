package com.khoinguyen.amela.model.dto.attendance;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceDtoRequest {
    Long attendanceId;

    @NotNull(message = "Check day is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate checkDay;

    @NotNull(message = "Check in time is required")
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime checkInTime;

    @NotNull(message = "Check out time is required")
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime checkOutTime;
    Long userId;
    String note;
}
