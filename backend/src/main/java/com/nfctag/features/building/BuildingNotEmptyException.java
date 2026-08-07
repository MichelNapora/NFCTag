package com.nfctag.features.building;

import com.nfctag.common.Messages;

public class BuildingNotEmptyException extends RuntimeException {
    public BuildingNotEmptyException(long wings) {
        super(String.format(Messages.BUILDING_NOT_EMPTY, wings));
    }
}