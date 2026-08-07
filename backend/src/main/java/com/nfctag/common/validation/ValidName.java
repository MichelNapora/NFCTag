package com.nfctag.common.validation;

import com.nfctag.common.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default Messages.INVALID_NAME;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}