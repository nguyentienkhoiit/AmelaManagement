package com.khoinguyen.amela.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.khoinguyen.amela.model.dto.authentication.PasswordDtoRequest;
import com.khoinguyen.amela.validator.PasswordMatches;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordDtoRequest> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(PasswordDtoRequest passwordDtoRequest, ConstraintValidatorContext context) {
        boolean isValid = passwordDtoRequest.getPassword() != null
                && passwordDtoRequest.getPassword().equals(passwordDtoRequest.getConfirmPassword());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
