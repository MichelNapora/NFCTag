package com.nfctag.features.business.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BceValidator implements ConstraintValidator<ValidBce, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        // 10 chiffres, commence par 0 ou 1
        if (!value.matches("[01]\\d{9}")) {
            return false;
        }
        // clé de contrôle : les 2 derniers = 97 - (8 premiers mod 97)
        long base  = Long.parseLong(value.substring(0, 8));
        int  check = Integer.parseInt(value.substring(8));
        return (97 - (int) (base % 97)) == check;
    }
}