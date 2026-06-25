package com.nfctag.backoffice.dto;

/** Ligne d'agrégat (par société ou par bâtiment). */
public record StatRow(
        String label,
        long passages,
        long totalMinutes) {
}
