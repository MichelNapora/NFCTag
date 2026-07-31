package com.nfctag.features.business;

public class BusinessAlreadyExistsException extends RuntimeException {
    public BusinessAlreadyExistsException(String bce) {
        super("BCE already exists : " + bce);
    }
}