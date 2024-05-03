package com.khoinguyen.amela.model.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDtoUpdate {
    Long id = 0L;

    String code;

    @Pattern(regexp = "^[a-zA-Z0-9.]+@(\\w+\\.)*(\\w+)$", message = "Email is invalid")
    String email;

    @Length(min = 3, max = 15, message = "Length greater than 3 and smaller than 16")
    String firstname;

    @Length(min = 3, max = 15, message = "Length greater than 3 and smaller than 16")
    String lastname;

    @Length(min = 4, message = "Username has length greater than 3")
    String username;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone just include 10 or 11 digit")
    String phone;

    String address;

    @NotNull(message = "Date of birth is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;

    int gender;

    Long departmentId;

    Long jobPositionId;

    Long roleId;
}
