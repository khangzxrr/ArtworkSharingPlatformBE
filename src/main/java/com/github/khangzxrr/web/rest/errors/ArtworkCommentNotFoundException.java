package com.github.khangzxrr.web.rest.errors;

public class ArtworkCommentNotFoundException extends BadRequestAlertException {

    public ArtworkCommentNotFoundException() {
        super("Artwork comment not found", "artworkComment", "artworkCommentNotFoundException");
    }
}
