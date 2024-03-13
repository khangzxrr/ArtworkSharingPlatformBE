package com.github.khangzxrr.web.rest.errors;

public class ArtworkSellingIsFinishedException extends BadRequestAlertException {

    public ArtworkSellingIsFinishedException() {
        super("Artwork selling is finished exception", "artworkSelling", "artworkSellingIsFinished");
    }
}
