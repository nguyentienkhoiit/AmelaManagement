package com.khoinguyen.amela.model.dto.attendance;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
