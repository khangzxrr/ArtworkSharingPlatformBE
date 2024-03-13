package com.github.khangzxrr.web.rest.errors;

public class ArtworkSellingIsNotAuctionException extends BadRequestAlertException {

    public ArtworkSellingIsNotAuctionException() {
        super("Artwork selling is not an auction", "artworkSelling", "artworkSellingIsNotAuction");
    }
}
