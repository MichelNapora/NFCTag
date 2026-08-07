package com.nfctag.features.technician;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.business.Business;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Technician {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    @Size(max = 50)
    private String firstname;

    @NotBlank
    @Size(max = 50)
    private String lastname;

    @NotBlank
    @Column(unique = true)
    private String mobile;

    @ManyToOne(optional = false)
    @JoinColumn(name="business_id", nullable = false)
    private Business business;

    @Column(unique = true, updatable = false, nullable = false)
    private UUID deviceToken = UUID.randomUUID();

    public Technician(String firstname, String lastname, String mobile, Business business){
        this.firstname=firstname;
        this.lastname=lastname;
        this.mobile=mobile;
        this.business=business;
    }

    public Technician(){}

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

    public Business getBusiness(){
        return this.business;
    }

    public UUID getDeviceToken(){
        return this.deviceToken;
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

    public void setBusiness(Business business){
        this.business=business;
    }


}
