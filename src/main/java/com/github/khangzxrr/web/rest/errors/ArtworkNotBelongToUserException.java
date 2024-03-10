package com.github.khangzxrr.web.rest.errors;

public class ArtworkNotBelongToUserException extends BadRequestAlertException {

    public ArtworkNotBelongToUserException() {
        super("Artwork is not belong to user", "artwork", "artworkNotBelongToUser");
    }
}
