package com.nfctag.features.building;

import com.nfctag.common.Messages;

import java.util.UUID;

public class BuildingNotFoundException extends RuntimeException {
    public BuildingNotFoundException(UUID id) {
        super(String.format(Messages.BUILDING_NOT_FOUND, id));
    }
}
