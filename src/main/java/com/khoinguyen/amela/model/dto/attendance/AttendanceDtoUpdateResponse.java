package com.khoinguyen.amela.model.dto.attendance;

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
public class AttendanceDtoUpdateResponse {
    Long attendanceId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime checkInTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime checkOutTime;
    Long userId;
    String note;
}
