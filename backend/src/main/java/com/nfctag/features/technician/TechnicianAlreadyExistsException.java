package com.nfctag.features.technician;

public class TechnicianAlreadyExistsException extends RuntimeException {
    public TechnicianAlreadyExistsException(String mobile) {
        super("Mobile already exists : " + mobile);
    }
}