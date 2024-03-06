package com.github.khangzxrr.web.rest.errors;

public class ArtworkInPrivateException extends BadRequestAlertException {

    public ArtworkInPrivateException() {
        super("Artwork is in private, cannot perform action", "artwork", "ArtworkInPrivate");
    }
}
