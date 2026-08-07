package com.nfctag.features.scan;

import com.nfctag.common.validation.ValidName;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class ScanRequestDTO {

    private UUID deviceToken;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitude;

    @DecimalMin("0.0")
    private Double accuracy;


    @ValidName
    @Size(min = 2, max = 50)
    private String firstname;


    @ValidName
    @Size(min = 2, max = 50)
    private String lastname;

    private String mobile;
    private UUID businessId;

    public ScanRequestDTO(UUID deviceToken, Double latitude, Double longitude, Double accuracy, String firstname, String lastname, String mobile, UUID businessId){
        this.deviceToken=deviceToken;
        this.latitude=latitude;
        this.longitude=longitude;
        this.accuracy=accuracy;
        this.firstname=firstname;
        this.lastname=lastname;
        this.mobile=mobile;
        this.businessId=businessId;
    }

    public ScanRequestDTO(){}

    public UUID getDeviceToken(){
        return this.deviceToken;
    }

    public Double getLatitude(){
        return this.latitude;
    }

    public Double getLongitude(){
        return this.longitude;
    }

    public Double getAccuracy(){
        return this.accuracy;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public String getMobile(){
        return this.mobile;
    }

    public UUID getBusinessId(){
        return this.businessId;
    }

    public void setDeviceToken(UUID deviceToken){
        this.deviceToken=deviceToken;
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

    public void setFirstname(String firstname){
        this.firstname=firstname;
    }

    public void setLastname(String lastname){
        this.lastname=lastname;
    }

    public void setMobile(String mobile){
        this.mobile=mobile;
    }

    public void setBusinessId(UUID businessId){
        this.businessId=businessId;
    }

}