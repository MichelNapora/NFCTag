package com.nfctag.building.dto;

/** Bâtiment vu côté administration. */
public record BuildingAdminDto(
        Long id,
        String name,
        String projectCode,
        String buildingType,
        String city) {
}
