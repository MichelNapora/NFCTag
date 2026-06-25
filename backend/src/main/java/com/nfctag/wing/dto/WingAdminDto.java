package com.nfctag.wing.dto;

/** Aile vue côté administration. */
public record WingAdminDto(
        Long id,
        String name,
        Long buildingId,
        String buildingName) {
}
