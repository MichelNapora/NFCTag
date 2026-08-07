package com.nfctag.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<ValidMobile, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        // Une seule écriture possible : la colonne est unique et le mobile identifie le technicien.
        // Si « 0470111222 » et « +32470111222 » passaient tous les deux, ce serait deux personnes.
        return value.matches("04\\d{8}");
    }
}