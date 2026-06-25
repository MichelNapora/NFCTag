package com.nfctag.scan.dto;

/** Infos du tag (bâtiment/aile) affichées au technicien. */
public record TagInfo(
        String tagName,
        String buildingName,
        String wingName) {
}
