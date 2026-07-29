package com.nfctag.features.location;

public class InsufficientAccuracyException extends RuntimeException {
    public InsufficientAccuracyException(String message) {
        super(message);
    }
}
