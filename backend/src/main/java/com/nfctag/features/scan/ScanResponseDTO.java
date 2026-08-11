package com.nfctag.features.scan;

import com.nfctag.features.location.LocationStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ScanResponseDTO {

    private UUID deviceToken;
    private String technicianName;
    private String businessName;
    private String buildingName;
    private String wingName;
    private OffsetDateTime arrivedAt;
    private OffsetDateTime departedAt;
    private boolean locationVerified;
    private LocationStatus locationStatus;
    private Double distanceMeters;
    private ScanAction action;

    public ScanResponseDTO(UUID deviceToken, String technicianName, String businessName,
                           String buildingName, String wingName,
                           OffsetDateTime arrivedAt, OffsetDateTime departedAt,
                           boolean locationVerified, LocationStatus locationStatus, Double distanceMeters,
                           ScanAction action){
        this.deviceToken=deviceToken;
        this.technicianName=technicianName;
        this.businessName=businessName;
        this.buildingName=buildingName;
        this.wingName=wingName;
        this.arrivedAt=arrivedAt;
        this.departedAt=departedAt;
        this.locationVerified=locationVerified;
        this.locationStatus=locationStatus;
        this.distanceMeters=distanceMeters;
        this.action=action;
    }

    public ScanResponseDTO(){}

    public UUID getDeviceToken(){
        return this.deviceToken;
    }

    public String getTechnicianName(){
        return this.technicianName;
    }

    public String getBusinessName(){
        return this.businessName;
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

    public LocationStatus getLocationStatus(){
        return this.locationStatus;
    }

    public Double getDistanceMeters(){
        return this.distanceMeters;
    }

    public ScanAction getAction(){
        return this.action;
    }

}