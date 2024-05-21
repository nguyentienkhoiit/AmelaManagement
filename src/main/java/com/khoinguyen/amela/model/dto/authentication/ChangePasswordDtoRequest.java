package com.khoinguyen.amela.model.dto.authentication;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "{validation.required}")
    String oldPassword;

    @NotBlank(message = "{validation.required}")
    String newPassword;

    @NotBlank(message = "{validation.required}")
    String confirmPassword;
}
