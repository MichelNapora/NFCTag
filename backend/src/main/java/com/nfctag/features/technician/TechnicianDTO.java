package com.nfctag.features.technician;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.common.validation.ValidMobile;
import com.nfctag.common.validation.ValidName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.UUID;

public class TechnicianDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    @ValidName
    @Size(min = 2, max = 50)
    private String firstname;

    @NotBlank
    @ValidName
    @Size(min = 2, max = 50)
    private String lastname;

    @NotBlank
    @ValidMobile
    private String mobile;

    @NotNull
    private UUID businessId;

    public TechnicianDTO(UUID id, String firstname, String lastname, String mobile, UUID businessId){
        this.id=id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.mobile=mobile;
        this.businessId=businessId;
    }

    public TechnicianDTO(){}

    public UUID getId(){
        return this.id;
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
