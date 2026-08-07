package com.nfctag.features.technician;

import com.nfctag.common.Messages;

import java.util.UUID;

public class TechnicianNotFoundException extends RuntimeException {
    public TechnicianNotFoundException(UUID id) {
        super(String.format(Messages.TECHNICIAN_NOT_FOUND, id));
    }
}