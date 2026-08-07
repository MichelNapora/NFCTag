package com.nfctag.features.auth;

import com.nfctag.common.Messages;

public class SamePasswordException extends RuntimeException {
    public SamePasswordException() {
        super(Messages.SAME_PASSWORD);
    }
}