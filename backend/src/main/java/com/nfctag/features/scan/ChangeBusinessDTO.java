package com.nfctag.features.scan;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ChangeBusinessDTO {

    private UUID deviceToken;

    @NotNull
    private UUID businessId;

    public UUID getDeviceToken(){
        return this.deviceToken;
    }

    public void setDeviceToken(UUID deviceToken){
        this.deviceToken=deviceToken;
    }

    public UUID getBusinessId(){
        return this.businessId;
    }

    public void setBusinessId(UUID businessId){
        this.businessId=businessId;
    }
}