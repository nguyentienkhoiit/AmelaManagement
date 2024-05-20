package com.khoinguyen.amela.model.dto.attendance;

import com.khoinguyen.amela.validator.CheckDayNotInFuture;
import com.khoinguyen.amela.validator.ValidTimeOrder;
import com.khoinguyen.amela.validator.WithinDeadline;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ValidTimeOrder(message = "{validation.validTimeOrder}")
public class AttendanceDtoRequest {
    Long attendanceId;

    @WithinDeadline(message = "{validation.withinDeadline}", days = 3)
    @CheckDayNotInFuture
    @NotNull(message = "{validation.required}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate checkDay;

    @NotNull(message = "{validation.required}")
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime checkInTime;

    @NotNull(message = "{validation.required}")
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime checkOutTime;

    Long userId;

    String note;
}
