package com.nfctag.features.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.business.validation.ValidBce;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class BusinessDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    @ValidBce
    private String bce;

    public BusinessDTO(UUID id, String name, String bce){
        this.id=id;
        this.name=name;
        this.bce=bce;
    }

    public BusinessDTO(){}

    public UUID getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getBce(){
        return this.bce;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBce(String bce){
        this.bce=bce;
    }
}
