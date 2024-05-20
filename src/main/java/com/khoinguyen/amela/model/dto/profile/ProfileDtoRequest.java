package com.khoinguyen.amela.model.dto.profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    int gender;

    @Size(min = 2, max = 30, message = "{validation.length.between}")
    String firstname;

    @Size(min = 2, max = 30, message = "{validation.length.between}")
    String lastname;

    String address;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "{validation.phone.pattern}")
    String phone;

    @NotNull(message = "{validation.required}")
    LocalDate dateOfBirth;

    @Size(min = 4, max = 20, message = "{validation.length.between}")
    String username;

    String avatar;
}
