package com.khoinguyen.amela.model.dto.authentication;

import com.khoinguyen.amela.validator.PasswordMatches;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@PasswordMatches
public class PasswordDtoRequest {
    String password;
    String confirmPassword;
    String token;
}
