package com.nfctag.features.business;

import com.nfctag.common.Messages;

import java.util.UUID;

public class BusinessNotFoundException extends RuntimeException {
    public BusinessNotFoundException(UUID id) {
        super(String.format(Messages.BUSINESS_NOT_FOUND, id));
    }
}