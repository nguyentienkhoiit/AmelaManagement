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
@ValidTimeOrder(message = "Check out time must be after check in day")
public class AttendanceDtoRequest {
    Long attendanceId;

    @WithinDeadline(message = "It is past the deadline to add attendance", days = 3)
    @CheckDayNotInFuture
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
