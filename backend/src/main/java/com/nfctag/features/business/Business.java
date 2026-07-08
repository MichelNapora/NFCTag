package com.nfctag.features.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.business.validation.ValidBce;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity

public class Business {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    @ValidBce
    @Column(unique = true)
    private String bce;

    public Business(String name, String bce){
        this.name=name;
        this.bce=bce;
    }

    public Business(){}

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
