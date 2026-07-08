package com.nfctag.features.tag;

public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException(String message) {
        super(message);
    }
}
