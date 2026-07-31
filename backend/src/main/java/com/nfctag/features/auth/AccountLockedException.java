package com.nfctag.features.auth;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException() {
        super("Account locked");
    }
}