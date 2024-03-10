package com.github.khangzxrr.web.rest.errors;

public class UserAlreadyLikeArtworkException extends BadRequestAlertException {

    public UserAlreadyLikeArtworkException() {
        super("User already like artwork", "artwork", "UserAlreadyLikeArtwork");
    }
}
