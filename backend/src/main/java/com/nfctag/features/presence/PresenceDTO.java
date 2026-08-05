package com.nfctag.features.presence;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.location.LocationStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public class PresenceDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String technicianName;
    private String mobile;
    private String businessName;
    private String buildingName;
    private String wingName;
    private OffsetDateTime arrivedAt;
    private OffsetDateTime departedAt;
    private Long durationMinutes;
    private boolean estimated;
    private boolean locationVerified;
    private LocationStatus locationStatus;
    private Double distanceMeters;
    private LocationStatus departureLocationStatus;
    private Double departureDistanceMeters;

    public PresenceDTO() {}

    public PresenceDTO(UUID id, String technicianName, String mobile, String businessName, String buildingName, String wingName, OffsetDateTime arrivedAt, OffsetDateTime departedAt, Long durationMinutes, boolean estimated, boolean locationVerified, LocationStatus locationStatus, Double distanceMeters, LocationStatus departureLocationStatus, Double departureDistanceMeters) {
        this.id = id;
        this.technicianName = technicianName;
        this.mobile = mobile;
        this.businessName = businessName;
        this.buildingName = buildingName;
        this.wingName = wingName;
        this.arrivedAt = arrivedAt;
        this.departedAt = departedAt;
        this.durationMinutes = durationMinutes;
        this.estimated = estimated;
        this.locationVerified = locationVerified;
        this.locationStatus = locationStatus;
        this.distanceMeters = distanceMeters;
        this.departureLocationStatus=departureLocationStatus;
        this.departureDistanceMeters=departureDistanceMeters;
    }

    public UUID getId() {
        return this.id;
    }

    public String getTechnicianName() {
        return this.technicianName;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getBusinessName() {
        return this.businessName;
    }

    public String getBuildingName() {
        return this.buildingName;
    }

    public String getWingName() {
        return this.wingName;
    }

    public OffsetDateTime getArrivedAt() {
        return this.arrivedAt;
    }

    public OffsetDateTime getDepartedAt() {
        return this.departedAt;
    }

    public Long getDurationMinutes() {
        return this.durationMinutes;
    }

    public boolean isEstimated() {
        return estimated;
    }

    public boolean isLocationVerified() {
        return locationVerified;
    }

    public LocationStatus getLocationStatus() {
        return this.locationStatus;
    }

    public Double getDistanceMeters() {
        return this.distanceMeters;
    }

    public LocationStatus getDepartureLocationStatus() {
        return this.departureLocationStatus;
    }

    public Double getDepartureDistanceMeters() {
        return this.departureDistanceMeters;
    }
}