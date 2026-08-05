package com.nfctag.features.scan;

public class ScanIdentityMismatchException extends RuntimeException {
    public ScanIdentityMismatchException() {
        super("Identity does not match. Check your firstname, lastname and business.");
    }
}