package com.nfctag.features.location;

import com.nfctag.common.Messages;

public class InsufficientAccuracyException extends RuntimeException {
    public InsufficientAccuracyException(double accuracy) {
        super(String.format(Messages.LOCATION_INACCURATE, Math.round(accuracy)));
    }
}