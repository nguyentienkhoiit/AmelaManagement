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
@ToString
public class AttendanceDtoUpdateResponse {
    @NotNull(message = "{validation.required}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate checkDay;

    @NotNull(message = "{validation.required}")
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime checkInTime;

    @NotNull(message = "{validation.required}")
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime checkOutTime;

    Long attendanceId;
    boolean isExpired;
    Long userId;
    String note;
}
