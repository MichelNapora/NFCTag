package com.nfctag.features.wing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class WingDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private UUID buildingId;

    public WingDTO(UUID id,String name, UUID buildingId){
        this.id=id;
        this.name=name;
        this.buildingId=buildingId;
    }

    public WingDTO(){}

    public UUID getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public UUID getBuildingId(){
        return this.buildingId;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setBuildingId(UUID buildingId){
        this.buildingId=buildingId;
    }
}
