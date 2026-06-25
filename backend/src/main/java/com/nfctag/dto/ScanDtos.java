package com.nfctag.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTOs du flux de scan (côté technicien sur téléphone).
 */
public final class ScanDtos {

    private ScanDtos() {
    }

    // ---- Requêtes ----

    /** Scan initial : on présente le jeton appareil si on en a un. */
    public record ScanRequest(
            @NotBlank String tagToken,
            String deviceToken) {
    }

    /** Repli n°1 : le jeton est absent, on identifie par mobile. */
    public record LookupRequest(
            @NotBlank String tagToken,
            @NotBlank String mobile) {
    }

    /** Repli n°2 : mobile inconnu, le technicien choisit sa société (1ʳᵉ fois). */
    public record RegisterRequest(
            @NotBlank String tagToken,
            @NotBlank String mobile,
            @NotNull Long businessId,
            String firstname,
            String lastname) {
    }

    // ---- Réponses ----

    /** Infos du tag (bâtiment/aile) affichées au technicien. */
    public record TagInfo(
            String tagName,
            String buildingName,
            String wingName) {
    }

    /**
     * Résultat d'un scan.
     * - status = RECOGNIZED         → scan traité (voir action)
     * - status = NEED_IDENTIFICATION→ pas de jeton, demander le mobile
     * - status = NEED_BUSINESS      → mobile inconnu, demander la société
     */
    public record ScanResult(
            String status,
            String action,          // ARRIVAL | DEPARTURE | null
            String deviceToken,     // nouveau jeton à stocker dans le navigateur (sinon null)
            TagInfo tag,
            String workerName,
            String businessName,
            OffsetDateTime time,
            List<BusinessDto> businesses // proposé seulement quand NEED_BUSINESS
    ) {
        public static final String RECOGNIZED = "RECOGNIZED";
        public static final String NEED_IDENTIFICATION = "NEED_IDENTIFICATION";
        public static final String NEED_BUSINESS = "NEED_BUSINESS";

        public static final String ACTION_ARRIVAL = "ARRIVAL";
        public static final String ACTION_DEPARTURE = "DEPARTURE";
    }

    public record BusinessDto(Long id, String name) {
    }
}
