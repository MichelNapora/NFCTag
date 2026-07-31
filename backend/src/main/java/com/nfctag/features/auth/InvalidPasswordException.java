package com.nfctag.features.auth;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("This password is invalid");
    }
}