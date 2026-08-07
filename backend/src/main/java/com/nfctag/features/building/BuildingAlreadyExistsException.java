package com.nfctag.features.building;

import com.nfctag.common.Messages;

public class BuildingAlreadyExistsException extends RuntimeException {
    public BuildingAlreadyExistsException(String projectCode) {
        super(String.format(Messages.BUILDING_EXISTS, projectCode));
    }
}