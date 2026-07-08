package com.nfctag.features.scan;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ScanResponseDTO {

    private UUID deviceToken;
    private String technicianName;
    private String buildingName;
    private String wingName;
    private OffsetDateTime arrivedAt;
    private OffsetDateTime departedAt;
    private boolean locationVerified;
    private ScanAction action;

    public ScanResponseDTO(UUID deviceToken, String technicianName, String buildingName, String wingName, OffsetDateTime arrivedAt, OffsetDateTime departedAt, boolean locationVerified, ScanAction action){
        this.deviceToken=deviceToken;
        this.technicianName=technicianName;
        this.buildingName=buildingName;
        this.wingName=wingName;
        this.arrivedAt=arrivedAt;
        this.departedAt=departedAt;
        this.locationVerified=locationVerified;
        this.action=action;
    }

    public ScanResponseDTO(){}

    public UUID getDeviceToken(){
        return this.deviceToken;
    }

    public String getTechnicianName(){
        return this.technicianName;
    }

    public String getBuildingName(){
        return this.buildingName;
    }

    public String getWingName(){
        return this.wingName;
    }

    public OffsetDateTime getArrivedAt(){
        return this.arrivedAt;
    }

    public OffsetDateTime getDepartedAt(){
        return this.departedAt;
    }

    public boolean isLocationVerified(){
        return this.locationVerified;
    }

    public ScanAction getAction(){
        return this.action;
    }

}