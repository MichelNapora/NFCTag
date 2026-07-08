package com.nfctag.features.technician;

public class TechnicianAlreadyExistsException extends RuntimeException {
    public TechnicianAlreadyExistsException(String message) {
        super(message);
    }
}
