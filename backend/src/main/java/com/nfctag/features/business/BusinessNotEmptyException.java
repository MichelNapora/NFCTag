package com.nfctag.features.business;

public class BusinessNotEmptyException extends RuntimeException {
    public BusinessNotEmptyException(long technicians) {
        super("This company has " + technicians + " technician(s).");
    }
}