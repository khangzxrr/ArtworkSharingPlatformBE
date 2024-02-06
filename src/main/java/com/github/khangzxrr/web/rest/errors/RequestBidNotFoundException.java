package com.github.khangzxrr.web.rest.errors;

public class RequestBidNotFoundException extends BadRequestAlertException {

    public RequestBidNotFoundException() {
        super(ErrorConstants.REQUEST_BID_IS_NOT_FOUND, "Request bid is not found", "requestBid", "requestBidIsNotFound");
    }
}
