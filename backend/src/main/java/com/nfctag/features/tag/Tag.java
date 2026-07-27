package com.nfctag.features.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.wing.Wing;
import jakarta.persistence.*;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Tag {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Column(unique = true, updatable = false, nullable = false)
    private UUID scanToken = UUID.randomUUID();

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    private OffsetDateTime calibratedAt;

    @OneToOne(optional = false)
    @JoinColumn(name="wing_id",nullable = false,unique = true)
    private Wing wing;

    public Tag(Wing wing, Double latitude, Double longitude){
        this.wing=wing;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Tag(){}

    public UUID getId(){
        return this.id;
    }

    public UUID getScanToken(){
        return this.scanToken;
    }

    public Double getLatitude(){
        return this.latitude;
    }

    public Double getLongitude(){
        return this.longitude;
    }

    public Wing getWing(){
        return this.wing;
    }

    public OffsetDateTime getCalibratedAt(){
        return this.calibratedAt;
    }

    public void setLatitude(Double latitude){
        this.latitude=latitude;
    }

    public void setLongitude(Double longitude){
        this.longitude=longitude;
    }

    public void setWing(Wing wing){
        this.wing=wing;
    }

    public void setCalibratedAt(OffsetDateTime calibratedAt){
        this.calibratedAt=calibratedAt;
    }
}
