package com.nfctag.features.stats;

import org.springframework.stereotype.Component;

@Component
public class StatsMapper {

    public TechnicianStatsDTO toDto(TechnicianStats s){
        return new TechnicianStatsDTO(
                s.technicianId(),
                s.technicianName(),
                s.businessName(),
                s.totalScans(),
                s.locatedScans(),
                s.tooFarScans(),
                s.locatedRate()
        );
    }
}