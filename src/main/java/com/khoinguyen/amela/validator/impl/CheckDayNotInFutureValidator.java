package com.khoinguyen.amela.validator.impl;

import com.khoinguyen.amela.validator.CheckDayNotInFuture;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class CheckDayNotInFutureValidator implements ConstraintValidator<CheckDayNotInFuture, LocalDate> {

    @Override
    public void initialize(CheckDayNotInFuture constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate checkDay, ConstraintValidatorContext context) {
        if (checkDay == null) {
            return true; // @NotNull sẽ xử lý trường hợp null
        }
        LocalDate today = LocalDate.now();
        return !checkDay.isAfter(today);
    }
}
