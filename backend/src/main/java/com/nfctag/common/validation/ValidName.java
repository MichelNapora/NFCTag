package com.nfctag.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "Invalid name : letters, spaces, hyphens and apostrophes only";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}