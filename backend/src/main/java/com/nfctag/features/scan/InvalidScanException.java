package com.nfctag.features.scan;

import com.nfctag.common.Messages;

public class InvalidScanException extends RuntimeException {
    public InvalidScanException() {
        super(Messages.SCAN_INCOMPLETE);
    }
}