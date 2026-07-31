package com.nfctag.features.location;

public class InsufficientAccuracyException extends RuntimeException {
    public InsufficientAccuracyException(double accuracy) {
        super("GPS accuracy is too low (" + Math.round(accuracy) + " m). Go outside and try again.");
    }
}