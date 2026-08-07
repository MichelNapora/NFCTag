package com.nfctag.features.tag;

import com.nfctag.common.Messages;

public class TagNotEmptyException extends RuntimeException {
    public TagNotEmptyException(long presences) {
        super(String.format(Messages.TAG_NOT_EMPTY, presences));
    }
}