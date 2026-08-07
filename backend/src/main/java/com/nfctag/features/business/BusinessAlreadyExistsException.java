package com.nfctag.features.business;

import com.nfctag.common.Messages;

public class BusinessAlreadyExistsException extends RuntimeException {
    public BusinessAlreadyExistsException(String bce) {
        super(String.format(Messages.BUSINESS_EXISTS, bce));
    }
}