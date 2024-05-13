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
@ToString
public class AttendanceDtoUpdateResponse {
    Long attendanceId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime checkInTime = null;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime checkOutTime = null;
    Long userId;
    String note;
}
