package com.khoinguyen.amela.model.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordDtoRequest {

    @NotNull(message = "Old password is required")
    @NotBlank(message = "Old password is required")
    String oldPassword;

    @NotNull(message = "New password is required")
    @NotBlank(message = "New password is required")
    String newPassword;

    @NotNull(message = "Confirm password is required")
    @NotBlank(message = "Confirm password is required")
    String confirmPassword;
}
