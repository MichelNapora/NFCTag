package com.nfctag.features.wing;

import com.nfctag.common.Messages;

import java.util.UUID;

public class WingNotFoundException extends RuntimeException {
    public WingNotFoundException(UUID id) {
        super(String.format(Messages.WING_NOT_FOUND, id));
    }
}