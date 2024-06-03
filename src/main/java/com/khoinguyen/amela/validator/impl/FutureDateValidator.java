package com.khoinguyen.amela.validator.impl;

import java.time.LocalDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.khoinguyen.amela.validator.FutureDate;

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
