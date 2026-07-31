package com.nfctag.features.wing;

import java.util.UUID;

public class WingNotFoundException extends RuntimeException {
    public WingNotFoundException(UUID id) {
        super("Wing not found : " + id);
    }
}