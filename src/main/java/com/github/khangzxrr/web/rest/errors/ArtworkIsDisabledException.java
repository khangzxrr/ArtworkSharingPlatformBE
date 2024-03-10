package com.github.khangzxrr.web.rest.errors;

public class ArtworkIsDisabledException extends BadRequestAlertException {

    public ArtworkIsDisabledException() {
        super("Artwork is disabled, cannot perform action", "artwork", "ArtworkDisabled");
    }
}
