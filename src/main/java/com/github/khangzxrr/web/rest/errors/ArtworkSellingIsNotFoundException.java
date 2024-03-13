package com.github.khangzxrr.web.rest.errors;

public class ArtworkSellingIsNotFoundException extends BadRequestAlertException {

    public ArtworkSellingIsNotFoundException() {
        super("Artwork selling is not found exception", "artworkSelling", "ArtworkSellingNotFound");
    }
}
