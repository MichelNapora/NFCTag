package com.nfctag.features.scan;

import com.nfctag.common.Messages;

public class TooManyScansException extends RuntimeException {
    public TooManyScansException(int scansPerMinute) {
        super(String.format(Messages.SCAN_TOO_MANY, scansPerMinute));
    }
}