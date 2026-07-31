package com.nfctag.features.tag;

public class TagNotEmptyException extends RuntimeException {
    public TagNotEmptyException(long presences) {
        super("This tag has " + presences + " intervention(s) recorded.");
    }
}