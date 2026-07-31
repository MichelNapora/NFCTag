package com.nfctag.features.tag;

public class TagNotEmptyException extends RuntimeException {
    public TagNotEmptyException(String message) {
        super(message);
    }
}
