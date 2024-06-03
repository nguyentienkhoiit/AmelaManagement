package com.khoinguyen.amela.validator.impl;

import java.time.LocalTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoRequest;
import com.khoinguyen.amela.validator.ValidTimeOrder;

public class ValidTimeOrderValidator implements ConstraintValidator<ValidTimeOrder, AttendanceDtoRequest> {

    @Override
    public void initialize(ValidTimeOrder constraintAnnotation) {}

    @Override
    public boolean isValid(AttendanceDtoRequest request, ConstraintValidatorContext context) {
        LocalTime checkInTime = request.getCheckInTime();
        LocalTime checkOutTime = request.getCheckOutTime();

        if (checkInTime == null || checkOutTime == null) {
            return true; // @NotNull sẽ xử lý trường hợp null
        }

        boolean isValid = checkInTime.isBefore(checkOutTime);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("checkOutTime")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
