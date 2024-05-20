package com.khoinguyen.amela.validator.impl;

import com.khoinguyen.amela.validator.WithinDeadline;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class WithinDeadlineValidator implements ConstraintValidator<WithinDeadline, LocalDate> {
    private int days;

    @Override
    public void initialize(WithinDeadline constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate checkDay, ConstraintValidatorContext context) {
        if (checkDay == null) {
            return true;
        }
        LocalDate today = LocalDate.now();
        return !checkDay.isAfter(today.minusDays(days));
    }
}
