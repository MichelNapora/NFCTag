package com.nfctag.features.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class AddressDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    private String street;

    @Positive
    private int number;

    private String box;


    @Min(4000)
    @Max(4999)
    private int postalCode;

    @NotBlank
    private String city;

    public AddressDTO(UUID id, String street, int number, String box, int postalCode, String city){
        this.id =id;
        this.street=street;
        this.number=number;
        this.box=box;
        this.postalCode=postalCode;
        this.city=city;
    }

    public AddressDTO(){}

    public UUID getId(){
        return this.id;
    }

    public String getStreet(){
        return this.street;
    }

    public int getNumber(){
        return this.number;
    }

    public String getBox(){
        return this.box;
    }

    public int getPostalCode(){
        return  this.postalCode;
    }

    public String getCity(){
        return this.city;
    }

    public void setStreet(String street){
        this.street=street;
    }

    public void setNumber(int number){
        this.number=number;
    }

    public void setBox(String box){
        this.box=box;
    }

    public void setPostalCode(int postalCode){
        this.postalCode=postalCode;
    }

    public void setCity(String city){
        this.city=city;
    }

}
