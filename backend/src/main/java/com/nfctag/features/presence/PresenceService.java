package com.nfctag.features.presence;

import com.nfctag.features.location.LocationStatus;
import com.nfctag.features.technician.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PresenceService {

    @Autowired
    private PresenceRepository presenceRepository;

    public List<Presence> findAll(){
        return this.presenceRepository.findAll();
    }

    public List<Presence> findByTechnicianId(UUID technicianId) {
        return this.presenceRepository.findByTechnicianId(technicianId);
    }

    public List<Presence> findByMobile(String mobile) {
        return this.presenceRepository.findByTechnicianMobile(mobile);
    }

    public List<Presence> findByBuildingId(UUID buildingId) {
        return this.presenceRepository.findByTagWingBuildingId(buildingId);
    }

    public List<Presence> findByTagId(UUID tagId) {
        return this.presenceRepository.findByTagId(tagId);
    }

    /** Fiabilité de localisation par technicien : détecte ceux qui coupent leur GPS. */
    public List<TechnicianStats> statsByTechnician(){
        Map<UUID, List<Presence>> byTechnician = this.presenceRepository.findAll().stream()
                .collect(Collectors.groupingBy(p -> p.getTechnician().getId()));

        return byTechnician.values().stream()
                .map(this::buildStats)
                .sorted(Comparator.comparing(
                        TechnicianStats::locatedRate,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    private TechnicianStats buildStats(List<Presence> presences){
        Technician technician = presences.get(0).getTechnician();

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

        return new TechnicianStats(
                technician.getId(),
                technician.getFirstname() + " " + technician.getLastname(),
                technician.getBusiness().getName(),
                presences.size(),
                located,
                tooFar,
                rate
        );
    }
    public void delete(UUID id){
        if (!this.presenceRepository.existsById(id)) {
            throw new PresenceNotFoundException("Presence not found : " + id);
        }
        this.presenceRepository.deleteById(id);
    }

}
