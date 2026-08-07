package com.nfctag.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BceValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBce {
    String message() default "Invalid BCE number : 10 digits, starting with 0 or 1, wrong check digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}