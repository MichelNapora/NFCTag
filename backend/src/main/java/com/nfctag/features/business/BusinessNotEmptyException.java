package com.nfctag.features.business;

import com.nfctag.common.Messages;

public class BusinessNotEmptyException extends RuntimeException {
    public BusinessNotEmptyException(long technicians) {
        super(String.format(Messages.BUSINESS_NOT_EMPTY, technicians));
    }
}