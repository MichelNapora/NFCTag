package com.nfctag.backoffice.dto;

import java.util.List;

/** Synthèse globale. */
public record Stats(
        long totalPassages,
        long totalMinutes,
        long ongoing,
        long estimated,
        List<StatRow> byBusiness,
        List<StatRow> byBuilding) {
}
