package com.nfctag.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SpiEmailValidator implements ConstraintValidator<SpiEmail, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return value.matches("[a-z-]+\\.[a-z-]+@spi\\.be");
    }
}