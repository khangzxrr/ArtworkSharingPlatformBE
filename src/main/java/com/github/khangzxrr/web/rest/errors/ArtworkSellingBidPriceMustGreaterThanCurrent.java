package com.github.khangzxrr.web.rest.errors;

public class ArtworkSellingBidPriceMustGreaterThanCurrent extends BadRequestAlertException {

    public ArtworkSellingBidPriceMustGreaterThanCurrent() {
        super("Artwork selling bid price must greater than current", "artworkSelling", "artworkSellingBidPriceMustGreaterThanCurrent");
    }
}
