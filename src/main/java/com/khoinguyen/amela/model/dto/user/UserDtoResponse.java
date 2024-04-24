package com.khoinguyen.amela.model.dto.user;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {
    Long id;
    String code;
    String email;
    String firstname;
    String lastname;
    int gender;
    String username;
    String phone;
    String address;
    boolean status;
    String dateOfBirth;
    String department;
    String role;
    String position;
    boolean active;
}
