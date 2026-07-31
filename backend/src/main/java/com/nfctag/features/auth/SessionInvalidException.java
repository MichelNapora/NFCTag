package com.nfctag.features.auth;

public class SessionInvalidException extends RuntimeException {
    public SessionInvalidException() {
        super("Session invalid");
    }
}