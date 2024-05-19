package com.khoinguyen.amela.validator;

import com.khoinguyen.amela.validator.impl.WithinDeadlineValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = WithinDeadlineValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface WithinDeadline {
    String message() default "Check day cannot be more than 3 days before today";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int days() default 3;
}
