package com.nfctag.features.business.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BceValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBce {
    String message() default "Numéro BCE invalide (10 chiffres, début 0/1, clé de contrôle incorrecte)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}