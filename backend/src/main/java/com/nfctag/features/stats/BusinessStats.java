package com.nfctag.features.stats;

import java.util.UUID;

/** Résultat calculé : fiabilité de localisation d'une société. */
public record BusinessStats(UUID businessId, String businessName, long technicianCount,
                            long totalScans, long locatedScans, long tooFarScans, Integer locatedRate) {}