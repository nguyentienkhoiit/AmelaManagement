package com.khoinguyen.amela.model.dto.authentication;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordDtoRequest {
    String password;
    String confirmPassword;
    String token;
}
