package com.nfctag.features.building;

public class BuildingAlreadyExistsException extends RuntimeException {
    public BuildingAlreadyExistsException(String message) {
        super(message);
    }
}
