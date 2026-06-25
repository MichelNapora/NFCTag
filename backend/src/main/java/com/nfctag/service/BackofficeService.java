package com.nfctag.service;

import com.nfctag.config.NfctagProperties;
import com.nfctag.domain.*;
import com.nfctag.dto.BackofficeDtos.*;
import com.nfctag.repository.PresenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BackofficeService {

    private final PresenceRepository presenceRepository;
    private final NfctagProperties properties;

    public BackofficeService(PresenceRepository presenceRepository, NfctagProperties properties) {
        this.presenceRepository = presenceRepository;
        this.properties = properties;
    }

    public List<PresenceView> listPresences() {
        return presenceRepository.findAllByOrderByArrivedAtDesc().stream()
                .map(this::toView)
                .toList();
    }

    public Stats computeStats() {
        List<Presence> all = presenceRepository.findAllByOrderByArrivedAtDesc();

        long totalPassages = all.size();
        long ongoing = all.stream().filter(p -> p.getDepartedAt() == null).count();
        long estimated = all.stream().filter(Presence::isEstimated).count();
        long totalMinutes = all.stream().mapToLong(this::durationMinutes).sum();

        Map<String, long[]> byBusiness = new LinkedHashMap<>();
        Map<String, long[]> byBuilding = new LinkedHashMap<>();
        for (Presence p : all) {
            accumulate(byBusiness, businessName(p), durationMinutes(p));
            accumulate(byBuilding, buildingName(p), durationMinutes(p));
        }

        return new Stats(totalPassages, totalMinutes, ongoing, estimated,
                toRows(byBusiness), toRows(byBuilding));
    }

    // ---- Helpers ----

    private PresenceView toView(Presence p) {
        return new PresenceView(
                p.getId(),
                workerName(p.getWorker()),
                p.getWorker().getMobile(),
                businessName(p),
                buildingName(p),
                wingName(p),
                p.getNfc().getName(),
                p.getArrivedAt(),
                p.getDepartedAt(),
                p.getDepartedAt() == null ? null : durationMinutes(p),
                p.isEstimated(),
                p.getDepartedAt() == null);
    }

    /** Durée en minutes ; une présence ouverte est valorisée à la durée estimée. */
    private long durationMinutes(Presence p) {
        OffsetDateTime end = p.getDepartedAt() != null
                ? p.getDepartedAt()
                : p.getArrivedAt().plusMinutes(properties.getEstimatedDurationMinutes());
        return Math.max(0, Duration.between(p.getArrivedAt(), end).toMinutes());
    }

    private static void accumulate(Map<String, long[]> map, String key, long minutes) {
        long[] agg = map.computeIfAbsent(key, k -> new long[2]);
        agg[0] += 1;          // passages
        agg[1] += minutes;    // minutes
    }

    private static List<StatRow> toRows(Map<String, long[]> map) {
        return map.entrySet().stream()
                .map(e -> new StatRow(e.getKey(), e.getValue()[0], e.getValue()[1]))
                .sorted((a, b) -> Long.compare(b.totalMinutes(), a.totalMinutes()))
                .toList();
    }

    private static String businessName(Presence p) {
        Business b = p.getWorker().getBusiness();
        return b != null ? b.getName() : "—";
    }

    private static String buildingName(Presence p) {
        Wing w = p.getNfc().getWing();
        return (w != null && w.getBuilding() != null) ? w.getBuilding().getName() : "—";
    }

    private static String wingName(Presence p) {
        Wing w = p.getNfc().getWing();
        return w != null ? w.getName() : "—";
    }

    private static String workerName(Worker w) {
        String full = ((w.getFirstname() == null ? "" : w.getFirstname()) + " "
                + (w.getLastname() == null ? "" : w.getLastname())).trim();
        return full.isEmpty() ? w.getMobile() : full;
    }
}
