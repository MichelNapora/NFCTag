package com.nfctag.features.technician;

public class TechnicianNotEmptyException extends RuntimeException {
    public TechnicianNotEmptyException(long presences) {
        super("This technician has " + presences + " intervention(s) recorded.");
    }
}