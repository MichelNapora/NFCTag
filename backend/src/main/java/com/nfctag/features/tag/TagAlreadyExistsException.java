package com.nfctag.features.tag;

import com.nfctag.common.Messages;

public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException() {
        super(Messages.TAG_EXISTS);
    }
}