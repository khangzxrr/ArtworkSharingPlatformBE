package com.github.khangzxrr.web.rest.errors;

public class SellingDurationMustGreaterThanZeroException extends BadRequestAlertException {

    public SellingDurationMustGreaterThanZeroException() {
        super("Selling durtaion must greater than zero", "artworkSelling", "SellingDurationMustGreaterThanZero");
    }
}
