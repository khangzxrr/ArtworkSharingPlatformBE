package com.github.khangzxrr.web.rest.errors;

public class ArtworkNotFoundException extends BadRequestAlertException {

    public ArtworkNotFoundException() {
        super("Artwork is not found", "artwork", "artworkNotFound");
    }
}
