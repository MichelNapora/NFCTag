package com.nfctag.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SpiEmailValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface SpiEmail {
    String message() default "The mail must be formatted as firstname.lastname@spi.be";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
