package com.nfctag.features.stats;

import com.nfctag.features.presence.Presence;

import java.util.List;

/** Indicateurs du tableau de bord : les compteurs et les dernières interventions. */
public record DashboardStats(long totalPassages, long totalMinutes, long ongoing,
                             long estimated, long suspect, List<Presence> recent) {}