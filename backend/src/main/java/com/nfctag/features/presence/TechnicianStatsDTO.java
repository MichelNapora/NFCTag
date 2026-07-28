package com.nfctag.features.presence;

import java.util.UUID;

public class TechnicianStatsDTO {

    private UUID technicianId;
    private String technicianName;
    private String businessName;
    private long totalScans;
    private long locatedScans;
    private long tooFarScans;
    /** % de scans où le GPS a répondu, ou null si non mesurable. */
    private Integer locatedRate;

    public TechnicianStatsDTO(UUID technicianId, String technicianName, String businessName,
                              long totalScans, long locatedScans, long tooFarScans, Integer locatedRate){
        this.technicianId=technicianId;
        this.technicianName=technicianName;
        this.businessName=businessName;
        this.totalScans=totalScans;
        this.locatedScans=locatedScans;
        this.tooFarScans=tooFarScans;
        this.locatedRate=locatedRate;
    }

    public TechnicianStatsDTO(){}

    public UUID getTechnicianId(){
        return this.technicianId;
    }

    public String getTechnicianName(){
        return this.technicianName;
    }

    public String getBusinessName(){
        return this.businessName;
    }

    public long getTotalScans(){
        return this.totalScans;
    }

    public long getLocatedScans(){
        return this.locatedScans;
    }

    public long getTooFarScans(){
        return this.tooFarScans;
    }

    public Integer getLocatedRate(){
        return this.locatedRate;
    }
}