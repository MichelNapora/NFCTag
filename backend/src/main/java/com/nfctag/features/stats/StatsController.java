package com.nfctag.features.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private StatsMapper statsMapper;

    @GetMapping("/stats/technicians")
    public List<TechnicianStatsDTO> byTechnician() {
        return this.statsService.byTechnician().stream()
                .map(statsMapper::toDto)
                .toList();
    }

    @GetMapping("/stats/businesses")
    public List<BusinessStatsDTO> byBusiness() {
        return this.statsService.byBusiness().stream()
                .map(statsMapper::toDto)
                .toList();
    }

    @GetMapping("/stats/dashboard")
    public DashboardStatsDTO dashboard() {
        return this.statsMapper.toDto(this.statsService.dashboard());
    }
    
}