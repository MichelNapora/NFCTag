package com.nfctag.features.presence;

import java.util.UUID;

public class PresenceNotFoundException extends RuntimeException {
    public PresenceNotFoundException(UUID id) {
        super("Presence not found : " + id);
    }
}