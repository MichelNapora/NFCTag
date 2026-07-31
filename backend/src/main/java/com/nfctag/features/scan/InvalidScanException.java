package com.nfctag.features.scan;

public class InvalidScanException extends RuntimeException {
    public InvalidScanException() {
        super("The first scan needs firstname, lastname, mobile and business");
    }
}