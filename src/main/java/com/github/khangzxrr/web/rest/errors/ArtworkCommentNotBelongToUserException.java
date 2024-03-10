package com.github.khangzxrr.web.rest.errors;

public class ArtworkCommentNotBelongToUserException extends BadRequestAlertException {

    public ArtworkCommentNotBelongToUserException() {
        super("Artwork comment is not belong to user", "artworkComment", "artworkCommentNotBelongToUser");
    }
}
