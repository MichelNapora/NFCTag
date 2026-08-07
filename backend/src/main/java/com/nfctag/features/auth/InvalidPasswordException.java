package com.nfctag.features.auth;

import com.nfctag.common.Messages;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super(Messages.INVALID_PASSWORD);
    }
}