package com.nfctag.features.stats;

import java.util.UUID;

/** Résultat calculé : fiabilité de localisation d'un technicien. */
public record TechnicianStats(UUID technicianId, String technicianName, String businessName,
                              long totalScans, long locatedScans, long tooFarScans, Integer locatedRate) {}