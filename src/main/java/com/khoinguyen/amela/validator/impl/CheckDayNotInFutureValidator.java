package com.khoinguyen.amela.validator.impl;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.khoinguyen.amela.validator.CheckDayNotInFuture;

public class CheckDayNotInFutureValidator implements ConstraintValidator<CheckDayNotInFuture, LocalDate> {

    @Override
    public void initialize(CheckDayNotInFuture constraintAnnotation) {}

    @Override
    public boolean isValid(LocalDate checkDay, ConstraintValidatorContext context) {
        if (checkDay == null) {
            return true; // @NotNull sẽ xử lý trường hợp null
        }
        LocalDate today = LocalDate.now();
        return !checkDay.isAfter(today);
    }
}
