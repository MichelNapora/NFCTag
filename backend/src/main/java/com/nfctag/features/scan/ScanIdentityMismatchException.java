package com.nfctag.features.scan;

import com.nfctag.common.Messages;

public class ScanIdentityMismatchException extends RuntimeException {
    public ScanIdentityMismatchException() {
        super(Messages.SCAN_IDENTITY);
    }
}