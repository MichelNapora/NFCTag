package com.nfctag.features.technician;

import java.util.UUID;

public class TechnicianNotFoundException extends RuntimeException {
    public TechnicianNotFoundException(UUID id) {
        super("Technician not found : " + id);
    }
}