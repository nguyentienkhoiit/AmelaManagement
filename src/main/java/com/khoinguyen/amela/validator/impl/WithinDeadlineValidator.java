package com.khoinguyen.amela.validator.impl;

import com.khoinguyen.amela.util.DateTimeHelper;
import com.khoinguyen.amela.validator.WithinDeadline;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
