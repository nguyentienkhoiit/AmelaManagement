package com.khoinguyen.amela.validator.impl;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.khoinguyen.amela.util.DateTimeHelper;
import com.khoinguyen.amela.validator.WithinDeadline;

public class WithinDeadlineValidator implements ConstraintValidator<WithinDeadline, LocalDate> {
    private int days;

    @Override
    public void initialize(WithinDeadline constraintAnnotation) {
        this.days = constraintAnnotation.days();
    }

    @Override
    public boolean isValid(LocalDate checkDay, ConstraintValidatorContext context) {
        if (checkDay == null) return true;
        return DateTimeHelper.isExpiredDay(checkDay, days);
    }
}
