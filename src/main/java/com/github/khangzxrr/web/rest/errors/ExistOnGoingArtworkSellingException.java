package com.github.khangzxrr.web.rest.errors;

public class ExistOnGoingArtworkSellingException extends BadRequestAlertException {

    public ExistOnGoingArtworkSellingException() {
        super("Exist on going artwork selling exception", "artworkSelling", "existOnGoingArtworkSelling");
    }
}
