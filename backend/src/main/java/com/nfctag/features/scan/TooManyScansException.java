package com.nfctag.features.scan;

public class TooManyScansException extends RuntimeException {
    public TooManyScansException(int scansPerMinute) {
        super("Too many scans on this tag : " + scansPerMinute + " per minute maximum. Wait a moment and try again.");
    }
}