package com.nfctag.building.dto;

import jakarta.validation.constraints.NotBlank;

/** Création d'un bâtiment, avec son adresse (facultative) saisie en ligne. */
public record CreateBuildingRequest(
        @NotBlank String name,
        String projectCode,
        String buildingType,
        String street,
        String number,
        String postalCode,
        String city) {
}
