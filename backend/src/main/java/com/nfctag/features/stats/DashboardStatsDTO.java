package com.nfctag.features.stats;

import com.nfctag.features.presence.PresenceDTO;

import java.util.List;

public class DashboardStatsDTO {

    private long totalPassages;
    private long totalMinutes;
    private long ongoing;
    private long estimated;
    private long suspect;
    private List<PresenceDTO> recent;

    public DashboardStatsDTO(long totalPassages, long totalMinutes, long ongoing,
                             long estimated, long suspect, List<PresenceDTO> recent){
        this.totalPassages=totalPassages;
        this.totalMinutes=totalMinutes;
        this.ongoing=ongoing;
        this.estimated=estimated;
        this.suspect=suspect;
        this.recent=recent;
    }

    public DashboardStatsDTO(){}

    public long getTotalPassages(){
        return this.totalPassages;
    }

    public long getTotalMinutes(){
        return this.totalMinutes;
    }

    public long getOngoing(){
        return this.ongoing;
    }

    public long getEstimated(){
        return this.estimated;
    }

    public long getSuspect(){
        return this.suspect;
    }

    public List<PresenceDTO> getRecent(){
        return this.recent;
    }
}