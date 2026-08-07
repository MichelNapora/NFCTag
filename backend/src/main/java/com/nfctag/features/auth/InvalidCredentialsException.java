package com.nfctag.features.auth;

import com.nfctag.common.Messages;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super(Messages.INVALID_CREDENTIALS);
    }
}