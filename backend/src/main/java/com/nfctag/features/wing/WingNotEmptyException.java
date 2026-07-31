package com.nfctag.features.wing;

public class WingNotEmptyException extends RuntimeException {
    public WingNotEmptyException() {
        super("This wing has a tag. Delete it first.");
    }
}