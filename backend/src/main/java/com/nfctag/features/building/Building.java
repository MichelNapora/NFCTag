package com.nfctag.features.building;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Building {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(min = 4, max = 8)
    @Column(unique = true)
    private String projectCode;

    @OneToOne (optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", nullable = false,unique = true)
    private Address address;

    public Building(String name, String projectCode, Address address){
        this.name=name;
        this.projectCode=projectCode;
        this.address=address;
    }

    public Building(){}

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

    public Address getAddress(){
        return this.address;
    }

    public void setAddress(Address address){
        this.address=address;
    }

}
