package com.khoinguyen.amela.validator.impl;

import com.khoinguyen.amela.validator.FutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FutureDateValidator implements ConstraintValidator<FutureDate, LocalDateTime> {

    @Override
    public void initialize(FutureDate constraintAnnotation) {
        // Initialization code if needed
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // or false, based on your requirements
        }
        return value.isAfter(LocalDateTime.now());
    }
}
