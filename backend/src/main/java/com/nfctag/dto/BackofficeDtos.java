package com.nfctag.dto;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTOs du back-office (consultation par les collègues).
 */
public final class BackofficeDtos {

    private BackofficeDtos() {
    }

    /** Une intervention (présence) enrichie et calculée. */
    public record PresenceView(
            Long id,
            String workerName,
            String mobile,
            String businessName,
            String buildingName,
            String wingName,
            String tagName,
            OffsetDateTime arrivedAt,
            OffsetDateTime departedAt,
            Long durationMinutes,
            boolean estimated,
            boolean ongoing) {
    }

    /** Ligne d'agrégat (par société ou par bâtiment). */
    public record StatRow(
            String label,
            long passages,
            long totalMinutes) {
    }

    /** Synthèse globale. */
    public record Stats(
            long totalPassages,
            long totalMinutes,
            long ongoing,
            long estimated,
            List<StatRow> byBusiness,
            List<StatRow> byBuilding) {
    }
}
