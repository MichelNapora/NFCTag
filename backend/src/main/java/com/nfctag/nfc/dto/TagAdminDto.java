package com.nfctag.nfc.dto;

/** Tag NFC vu côté administration, avec l'URL à encoder sur le tag physique. */
public record TagAdminDto(
        Long id,
        String name,
        Long wingId,
        String wingName,
        String buildingName,
        String scanToken,
        String url) {
}
