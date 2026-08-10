package com.nfctag.features.stats;

import com.nfctag.features.business.Business;
import com.nfctag.features.location.LocationStatus;
import com.nfctag.features.presence.PresenceDurationCalculator;
import com.nfctag.features.presence.Presence;
import com.nfctag.features.presence.PresenceRepository;
import com.nfctag.features.presence.PresenceState;
import com.nfctag.features.presence.PresenceSpecifications;
import com.nfctag.features.technician.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private PresenceDurationCalculator durationCalculator;

    /** Fiabilité de localisation par technicien : détecte ceux qui coupent leur GPS. */
    public List<TechnicianStats> byTechnician(){
        Map<UUID, List<Presence>> byTechnician = this.presenceRepository.findAll().stream()
                .collect(Collectors.groupingBy(p -> p.getTechnician().getId()));

        return byTechnician.values().stream()
                .map(this::buildTechnicianStats)
                .sorted(Comparator.comparing(
                        TechnicianStats::locatedRate,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    private TechnicianStats buildTechnicianStats(List<Presence> presences){
        Technician technician = presences.get(0).getTechnician();
        LocationCounters counters = countLocation(presences);

        return new TechnicianStats(
                technician.getId(),
                technician.getFirstname() + " " + technician.getLastname(),
                technician.getBusiness().getName(),
                presences.size(),
                counters.located(),
                counters.tooFar(),
                counters.rate()
        );
    }
    /** Même fiabilité, agrégée par société. */
    public List<BusinessStats> byBusiness(){
        Map<UUID, List<Presence>> byBusiness = this.presenceRepository.findAll().stream()
                .collect(Collectors.groupingBy(p -> p.getTechnician().getBusiness().getId()));

        return byBusiness.values().stream()
                .map(this::buildBusinessStats)
                .sorted(Comparator.comparing(
                        BusinessStats::locatedRate,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    private BusinessStats buildBusinessStats(List<Presence> presences){
        Business business = presences.get(0).getTechnician().getBusiness();
        LocationCounters counters = countLocation(presences);

        long technicians = presences.stream()
                .map(p -> p.getTechnician().getId())
                .distinct()
                .count();

        return new BusinessStats(
                business.getId(),
                business.getName(),
                technicians,
                presences.size(),
                counters.located(),
                counters.tooFar(),
                counters.rate()
        );
    }
    /** Compteurs de fiabilité, communs aux différentes statistiques. */
    private LocationCounters countLocation(List<Presence> presences){
        // Les tags non calibrés ne sont pas de la faute du technicien : hors calcul.
        List<Presence> measurable = presences.stream()
                .filter(p -> p.getLocationStatus() != null
                        && p.getLocationStatus() != LocationStatus.TAG_NOT_CALIBRATED
                        && p.getDepartureLocationStatus() != LocationStatus.TAG_NOT_CALIBRATED)
                .toList();

        // Une intervention n'est localisée que si ses deux bouts le sont.
        // Une intervention encore en cours n'a pas de départ : on ne la pénalise pas.
        long located = measurable.stream()
                .filter(p -> positioned(p.getLocationStatus())
                        && (p.getDepartedAt() == null || positioned(p.getDepartureLocationStatus())))
                .count();

        long tooFar = measurable.stream()
                .filter(p -> p.getLocationStatus() == LocationStatus.TOO_FAR
                        || p.getDepartureLocationStatus() == LocationStatus.TOO_FAR)
                .count();

        Integer rate = measurable.isEmpty()
                ? null
                : (int) Math.round(100.0 * located / measurable.size());

        return new LocationCounters(located, tooFar, rate);
    }

    /** Une position a été obtenue, qu'elle soit proche ou lointaine. */
    private boolean positioned(LocationStatus status){
        return status == LocationStatus.VERIFIED || status == LocationStatus.TOO_FAR;
    }

    /**
     * Somme des durées de toutes les interventions terminées.
     * On réutilise le calculateur de l'affichage : une seule règle, un seul endroit.
     */
    private long sumDurationMinutes(){
        return this.presenceRepository.findAll().stream()
                .map(this.durationCalculator::compute)
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();
    }


    /** Indicateurs du tableau de bord : 5 compteurs et 8 lignes, rien de plus. */
    public DashboardStats dashboard(){
        return new DashboardStats(
                this.presenceRepository.count(),
                this.sumDurationMinutes(),
                this.presenceRepository.countByDepartedAtIsNull(),
                this.presenceRepository.countByEstimatedTrue(),
                this.presenceRepository.count(PresenceSpecifications.withState(PresenceState.SUSPECT)),
                this.presenceRepository.findTop8ByOrderByArrivedAtDesc()
        );
    }
}