package com.nfctag.scan.dto;

import jakarta.validation.constraints.NotBlank;

/** Scan initial : on présente le jeton appareil si on en a un. */
public record ScanRequest(
        @NotBlank String tagToken,
        String deviceToken) {
}
