package com.nfctag.features.business;

import java.util.UUID;

public class BusinessNotFoundException extends RuntimeException {
    public BusinessNotFoundException(UUID id) {
        super("Business not found : " + id);
    }
}