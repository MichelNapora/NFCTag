package com.nfctag.features.wing;

public class WingAlreadyExistsException extends RuntimeException {
    public WingAlreadyExistsException(String name) {
        super("Wing " + name + " already exists for this building");
    }
}