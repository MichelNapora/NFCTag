package com.nfctag.features.building;

public class BuildingNotEmptyException extends RuntimeException {
    public BuildingNotEmptyException(long wings) {
        super("This building contains " + wings + " wing(s). Delete them first.");
    }
}