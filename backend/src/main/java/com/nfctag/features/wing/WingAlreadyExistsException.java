package com.nfctag.features.wing;

import com.nfctag.common.Messages;

public class WingAlreadyExistsException extends RuntimeException {
    public WingAlreadyExistsException(String name) {
        super(String.format(Messages.WING_EXISTS, name));
    }
}