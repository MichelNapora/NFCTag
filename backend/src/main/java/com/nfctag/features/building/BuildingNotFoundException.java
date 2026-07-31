package com.nfctag.features.building;

import java.util.UUID;

public class BuildingNotFoundException extends RuntimeException {
    public BuildingNotFoundException(UUID id) {
        super("Building not found : " + id);
    }
}