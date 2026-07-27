package com.nfctag.features.tag;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class TagPositionDTO {

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    @NotNull
    @DecimalMin("0.0")
    private Double accuracy;

    public TagPositionDTO(Double latitude, Double longitude, Double accuracy){
        this.latitude=latitude;
        this.longitude=longitude;
        this.accuracy=accuracy;
    }

    public TagPositionDTO(){}

    public Double getLatitude(){
        return this.latitude;
    }

    public Double getLongitude(){
        return this.longitude;
    }

    public Double getAccuracy(){
        return this.accuracy;
    }

    public void setLatitude(Double latitude){
        this.latitude=latitude;
    }

    public void setLongitude(Double longitude){
        this.longitude=longitude;
    }

    public void setAccuracy(Double accuracy){
        this.accuracy=accuracy;
    }
}