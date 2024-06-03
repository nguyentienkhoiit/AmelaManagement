package com.khoinguyen.amela.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.khoinguyen.amela.validator.impl.ValidTimeOrderValidator;

@Constraint(validatedBy = ValidTimeOrderValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeOrder {
    String message() default "Check out time must be after check in time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
