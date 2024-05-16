package com.khoinguyen.amela.model.dto.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDtoResponse {
    String email;
    String username;
    String department;
    String position;
    int gender;
    String role;
    String firstname;
    String lastname;
    String code;
    String address;
    String phone;
    String dateOfBirth;
    String avatar;
    boolean isEditUsername;
}
