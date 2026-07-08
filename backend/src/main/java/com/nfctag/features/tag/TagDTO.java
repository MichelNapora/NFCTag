package com.nfctag.features.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TagDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID scanToken;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    @NotNull
    private UUID wingId;

    public TagDTO(UUID id, UUID scanToken, Double latitude, Double longitude, UUID wingId){
        this.id=id;
        this.scanToken=scanToken;
        this.latitude=latitude;
        this.longitude=longitude;
        this.wingId=wingId;
    }

    public TagDTO(){}

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

    public UUID getWingId(){
        return this.wingId;
    }

    public void setLatitude(Double latitude){
        this.latitude=latitude;
    }

    public void setLongitude(Double longitude){
        this.longitude=longitude;
    }

    public void setWingId(UUID wingId){
        this.wingId=wingId;
    }
}
