package com.nfctag.features.building;

public class BuildingNotEmptyException extends RuntimeException {
    public BuildingNotEmptyException(String message) {
        super(message);
    }
}
