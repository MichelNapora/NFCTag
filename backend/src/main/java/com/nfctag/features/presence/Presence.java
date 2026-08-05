package com.nfctag.features.presence;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.nfctag.features.location.LocationStatus;
import com.nfctag.features.tag.Tag;
import com.nfctag.features.technician.Technician;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;


import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Presence {

    @Id
    @UuidGenerator(style= UuidGenerator.Style.TIME)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name="technician_id", nullable = false)
    private Technician technician;

    @ManyToOne(optional = false)
    @JoinColumn(name= "tag_id",nullable = false)
    private Tag tag;

    @NotNull
    private OffsetDateTime arrivedAt;

    private OffsetDateTime departedAt;

    private boolean estimated;

    private boolean locationVerified;

    @Enumerated(EnumType.STRING)
    private LocationStatus locationStatus;

    private Double distanceMeters;

    @Enumerated(EnumType.STRING)
    private LocationStatus departureLocationStatus;

    private Double departureDistanceMeters;

    public Presence(Technician technician, Tag tag, OffsetDateTime arrivedAt,
                    LocationStatus locationStatus, Double distanceMeters){
        this.technician=technician;
        this.tag=tag;
        this.arrivedAt=arrivedAt;
        this.locationStatus=locationStatus;
        this.distanceMeters=distanceMeters;
        this.locationVerified = locationStatus == LocationStatus.VERIFIED;
    }

    public Presence(){}

    public UUID getId(){
        return this.id;
    }

    public Technician getTechnician(){
        return this.technician;
    }

    public Tag getTag(){
        return this.tag;
    }

    public OffsetDateTime getArrivedAt(){
        return this.arrivedAt;
    }

    public OffsetDateTime getDepartedAt(){
        return this.departedAt;
    }

    public boolean isEstimated(){
        return this.estimated;
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

    public LocationStatus getDepartureLocationStatus(){
        return this.departureLocationStatus;
    }

    public Double getDepartureDistanceMeters(){
        return this.departureDistanceMeters;
    }

    public void setDepartedAt(OffsetDateTime departedAt){
        this.departedAt=departedAt;
    }

    public void setEstimated(boolean estimated){
        this.estimated=estimated;
    }

    public void setLocationVerified(boolean locationVerified){
        this.locationVerified=locationVerified;
    }

    public void setDepartureLocationStatus(LocationStatus departureLocationStatus){
        this.departureLocationStatus=departureLocationStatus;
    }

    public void setDepartureDistanceMeters(Double departureDistanceMeters){
        this.departureDistanceMeters=departureDistanceMeters;
    }
}
