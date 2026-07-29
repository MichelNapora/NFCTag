package com.nfctag.features.location;

public class LocationCheck {

    private final LocationStatus status;
    private final Double distanceMeters;

    public LocationCheck(LocationStatus status, Double distanceMeters){
        this.status=status;
        this.distanceMeters=distanceMeters;
    }

    public LocationStatus getStatus(){
        return this.status;
    }

    public Double getDistanceMeters(){
        return this.distanceMeters;
    }

    public boolean isVerified(){
        return this.status == LocationStatus.VERIFIED;
    }
}
