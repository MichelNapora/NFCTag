package com.nfctag.features.auth;

import com.nfctag.common.Messages;

public class SessionInvalidException extends RuntimeException {
    public SessionInvalidException() {
        super(Messages.SESSION_INVALID);
    }
}