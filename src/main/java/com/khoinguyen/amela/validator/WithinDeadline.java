package com.khoinguyen.amela.validator;

import com.khoinguyen.amela.validator.impl.WithinDeadlineValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.khoinguyen.amela.util.Constant.IN_DAY_EDITED;

@Constraint(validatedBy = WithinDeadlineValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithinDeadline {
    String message() default "Check day cannot be more than " + IN_DAY_EDITED + " days before today";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int days() default IN_DAY_EDITED;
}
