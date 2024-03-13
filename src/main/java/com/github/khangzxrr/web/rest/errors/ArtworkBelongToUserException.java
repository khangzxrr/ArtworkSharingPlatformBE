package com.github.khangzxrr.web.rest.errors;

public class ArtworkBelongToUserException extends BadRequestAlertException {

    public ArtworkBelongToUserException() {
        super("Artwork is belong to user", "artwork", "artworkBelongToUser");
    }
}
