package com.nfctag.features.stats;

import com.nfctag.features.presence.PresenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatsMapper {

    @Autowired
    private PresenceMapper presenceMapper;

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

    public DashboardStatsDTO toDto(DashboardStats s){
        return new DashboardStatsDTO(
                s.totalPassages(),
                s.totalMinutes(),
                s.ongoing(),
                s.estimated(),
                s.suspect(),
                s.recent().stream().map(presenceMapper::toDto).toList()
        );
    }
}