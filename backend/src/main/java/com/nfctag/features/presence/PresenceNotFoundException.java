package com.nfctag.features.presence;

import com.nfctag.common.Messages;

import java.util.UUID;

public class PresenceNotFoundException extends RuntimeException {
    public PresenceNotFoundException(UUID id) {
        super(String.format(Messages.PRESENCE_NOT_FOUND, id));
    }
}