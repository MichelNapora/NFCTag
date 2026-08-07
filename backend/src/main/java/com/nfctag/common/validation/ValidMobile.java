package com.nfctag.common.validation;

import com.nfctag.common.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MobileValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMobile {
    String message() default Messages.INVALID_MOBILE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}