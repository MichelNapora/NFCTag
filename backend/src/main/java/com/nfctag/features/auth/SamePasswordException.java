package com.nfctag.features.auth;

public class SamePasswordException extends RuntimeException {
    public SamePasswordException() {
        super("The new password must be different");
    }
}