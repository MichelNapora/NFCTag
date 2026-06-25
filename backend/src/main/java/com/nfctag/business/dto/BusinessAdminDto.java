package com.nfctag.business.dto;

/** Société vue côté administration (avec le numéro d'entreprise). */
public record BusinessAdminDto(Long id, String name, String bce) {
}
