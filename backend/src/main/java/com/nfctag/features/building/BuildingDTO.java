package com.nfctag.features.building;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.address.AddressDTO;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class BuildingDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(min = 4, max = 8)
    @Column(unique = true)
    private String projectCode;

    @NotNull
    @Valid
    private AddressDTO address;

    public BuildingDTO(UUID id, String name, String projectCode, AddressDTO address){
        this.id=id;
        this.name=name;
        this.projectCode=projectCode;
        this.address=address;
    }

    public BuildingDTO(){}

    public UUID getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getProjectCode(){
        return this.projectCode;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setProjectCode(String projectCode){
        this.projectCode=projectCode;
    }

    public AddressDTO getAddress(){
        return this.address;
    }

    public void setAddress(AddressDTO address){
        this.address=address;
    }

}
