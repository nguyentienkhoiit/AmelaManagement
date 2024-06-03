package com.khoinguyen.amela.model.dto.user;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
public class UserDtoRequest {

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

    @NotNull(message = "{validation.required}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;

    int gender;

    Long departmentId;

    Long jobPositionId;

    Long roleId;
}
