package com.nfctag.scan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Repli n°2 : mobile inconnu, le technicien choisit sa société (1ʳᵉ fois). */
public record RegisterRequest(
        @NotBlank String tagToken,
        @NotBlank String mobile,
        @NotNull Long businessId,
        String firstname,
        String lastname) {
}
