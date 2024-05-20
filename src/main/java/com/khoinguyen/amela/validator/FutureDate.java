package com.khoinguyen.amela.validator;

import com.khoinguyen.amela.validator.impl.FutureDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FutureDateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {
    String message() default "The publish at must be in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
