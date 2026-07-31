package com.nfctag.features.tag;

public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException() {
        super("This wing already has a tag !");
    }
}