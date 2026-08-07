package com.nfctag.features.wing;

import com.nfctag.common.Messages;

public class WingNotEmptyException extends RuntimeException {
    public WingNotEmptyException() {
        super(Messages.WING_NOT_EMPTY);
    }
}