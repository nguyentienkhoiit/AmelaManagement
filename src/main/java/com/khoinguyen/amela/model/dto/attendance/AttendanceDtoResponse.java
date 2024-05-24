package com.khoinguyen.amela.model.dto.attendance;

import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class AttendanceDtoResponse {
    Long id;
    String fullName;
    String checkInTime;
    String checkOutTime;
    String checkDay;
    String day;
    String workTime;
    UserDtoResponse user;
    boolean status;
    LocalDateTime createdAt;
    Long createdBy;
    LocalDateTime updateAt;
    Long updateBy;
    String note;
    boolean isExpired;
}
