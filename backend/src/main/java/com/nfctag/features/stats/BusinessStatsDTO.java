package com.nfctag.features.stats;

import java.util.UUID;

public class BusinessStatsDTO {

    private UUID businessId;
    private String businessName;
    private long technicianCount;
    private long totalScans;
    private long locatedScans;
    private long tooFarScans;
    /** % de scans où le GPS a répondu, ou null si non mesurable. */
    private Integer locatedRate;

    public BusinessStatsDTO(UUID businessId, String businessName, long technicianCount,
                            long totalScans, long locatedScans, long tooFarScans, Integer locatedRate){
        this.businessId=businessId;
        this.businessName=businessName;
        this.technicianCount=technicianCount;
        this.totalScans=totalScans;
        this.locatedScans=locatedScans;
        this.tooFarScans=tooFarScans;
        this.locatedRate=locatedRate;
    }

    public BusinessStatsDTO(){}

    public UUID getBusinessId(){
        return this.businessId;
    }

    public String getBusinessName(){
        return this.businessName;
    }

    public long getTechnicianCount(){
        return this.technicianCount;
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