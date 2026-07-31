package com.nfctag.features.business;

public class BusinessNotEmptyException extends RuntimeException {
    public BusinessNotEmptyException(String message) {
        super(message);
    }
}
