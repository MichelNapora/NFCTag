package com.nfctag.features.tag;

import com.nfctag.common.Messages;

import java.util.UUID;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(UUID id) {
        super(String.format(Messages.TAG_NOT_FOUND, id));
    }
}