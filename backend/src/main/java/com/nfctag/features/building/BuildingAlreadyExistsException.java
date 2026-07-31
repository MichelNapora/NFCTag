package com.nfctag.features.building;

public class BuildingAlreadyExistsException extends RuntimeException {
    public BuildingAlreadyExistsException(String projectCode) {
        super("Project code already exists : " + projectCode);
    }
}