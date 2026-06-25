package com.nfctag.scan.dto;

import jakarta.validation.constraints.NotBlank;

/** Repli n°1 : le jeton est absent, on identifie par mobile. */
public record LookupRequest(
        @NotBlank String tagToken,
        @NotBlank String mobile) {
}
