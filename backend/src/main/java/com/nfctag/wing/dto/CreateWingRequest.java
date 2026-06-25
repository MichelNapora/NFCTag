package com.nfctag.wing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWingRequest(
        @NotNull Long buildingId,
        @NotBlank String name) {
}
