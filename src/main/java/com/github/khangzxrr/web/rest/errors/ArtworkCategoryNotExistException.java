package com.github.khangzxrr.web.rest.errors;

public class ArtworkCategoryNotExistException extends BadRequestAlertException {

    public ArtworkCategoryNotExistException() {
        super("Artwork category is not exist", "ArtworkCategory", "artworkCategoryNotExist");
    }
}
