package com.nfctag.nfc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTagRequest(
        @NotNull Long wingId,
        @NotBlank String name) {
}
