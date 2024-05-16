package com.khoinguyen.amela.model.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDtoRequest {
    int gender;

    @Length(min = 3, max = 15, message = "Length greater than 3 and smaller than 16")
    String firstname;

    @Length(min = 3, max = 15, message = "Length greater than 3 and smaller than 16")
    String lastname;

    String address;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone just include 10 or 11 digit")
    String phone;

    @NotNull(message = "Date of birth is required")
    LocalDate dateOfBirth;

    @Length(min = 4, message = "Username has length greater than 3")
    String username;

    String avatar;
}
