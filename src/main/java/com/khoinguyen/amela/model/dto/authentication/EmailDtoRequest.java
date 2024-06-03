package com.khoinguyen.amela.model.dto.authentication;

import jakarta.validation.constraints.Pattern;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDtoRequest {
    @Pattern(regexp = "^[a-zA-Z0-9.]+@(\\w+\\.)*(\\w+)$", message = "{validation.invalid}")
    String email;
}
