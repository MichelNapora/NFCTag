package com.nfctag.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MobileValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMobile {
    String message() default "Invalid mobile number : 10 digits starting with 04, no spaces";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}