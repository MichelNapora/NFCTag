package com.nfctag.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        // Des groupes de lettres, séparés par un seul espace, trait d'union ou apostrophe.
        // Accepte « Jean-Pierre », « O'Brien », « Van der Berg », « Éric ». Refuse « Luc99 ».
        return value.trim().matches("\\p{L}+([ '\\-]\\p{L}+)*");
    }
}