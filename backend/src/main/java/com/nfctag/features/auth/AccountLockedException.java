package com.nfctag.features.auth;

import com.nfctag.common.Messages;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException() {
        super(Messages.ACCOUNT_LOCKED);
    }
}