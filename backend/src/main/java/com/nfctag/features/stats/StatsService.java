package com.nfctag.features.stats;

import com.nfctag.features.business.Business;
import com.nfctag.features.location.LocationStatus;
import com.nfctag.features.presence.Presence;
import com.nfctag.features.presence.PresenceRepository;
import com.nfctag.features.technician.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StatsService {

    @Autowired
    private PresenceRepository presenceRepository;

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
                        && p.getLocationStatus() != LocationStatus.TAG_NOT_CALIBRATED)
                .toList();

        long located = measurable.stream()
                .filter(p -> p.getLocationStatus() == LocationStatus.VERIFIED
                        || p.getLocationStatus() == LocationStatus.TOO_FAR)
                .count();

        long tooFar = measurable.stream()
                .filter(p -> p.getLocationStatus() == LocationStatus.TOO_FAR)
                .count();

        Integer rate = measurable.isEmpty()
                ? null
                : (int) Math.round(100.0 * located / measurable.size());

        return new LocationCounters(located, tooFar, rate);
    }
}