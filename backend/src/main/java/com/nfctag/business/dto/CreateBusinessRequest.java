package com.nfctag.business.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateBusinessRequest(
        @NotBlank String name,
        String bce) {
}
