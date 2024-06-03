package com.khoinguyen.amela.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDtoUpdate {
    Long id = 0L;

    String code;

    @Pattern(regexp = "^[a-zA-Z0-9.]+@(\\w+\\.)*(\\w+)$", message = "{validation.invalid}")
    String email;

    @Size(min = 2, max = 30, message = "{validation.length.between}")
    String firstname;

    @Size(min = 2, max = 30, message = "{validation.length.between}")
    String lastname;

    @Size(min = 4, message = "{validation.length.min}")
    String username;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "{validation.phone.pattern}")
    String phone;

    String address;

    @NotBlank(message = "{validation.required}")
    String dateOfBirth;

    int gender;

    Long departmentId;

    Long jobPositionId;

    Long roleId;
}
