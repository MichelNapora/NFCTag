package com.nfctag.features.business;

public class BusinessAlreadyExistsException extends RuntimeException {
    public BusinessAlreadyExistsException(String message) {
        super(message);
    }
}
