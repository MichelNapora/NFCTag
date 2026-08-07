package com.nfctag.features.technician;

import com.nfctag.common.Messages;

public class TechnicianNotEmptyException extends RuntimeException {
    public TechnicianNotEmptyException(long presences) {
        super(String.format(Messages.TECHNICIAN_NOT_EMPTY, presences));
    }
}