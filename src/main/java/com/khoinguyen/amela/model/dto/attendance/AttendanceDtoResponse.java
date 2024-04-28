package com.khoinguyen.amela.model.dto.attendance;

import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDtoResponse {
    Long id;
    String checkInTime;
    String checkOutTime;
    String checkDay;
    UserDtoResponse user;
    boolean status;
    LocalDateTime createdAt;
    Long createdBy;
    LocalDateTime updateAt;
    Long updateBy;
}
