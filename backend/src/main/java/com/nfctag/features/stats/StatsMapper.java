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

    public BusinessStatsDTO toDto(BusinessStats s){
        return new BusinessStatsDTO(
                s.businessId(),
                s.businessName(),
                s.technicianCount(),
                s.totalScans(),
                s.locatedScans(),
                s.tooFarScans(),
                s.locatedRate()
        );
    }
}