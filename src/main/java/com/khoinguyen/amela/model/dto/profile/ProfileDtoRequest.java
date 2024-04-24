package com.khoinguyen.amela.model.dto.profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDtoRequest {
    String firstname;
    String lastname;
    int gender;
    String address;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone just include 10 or 11 digit")
    String phone;

    @NotNull(message = "Date of birth is required")
    LocalDate dateOfBirth;
}
