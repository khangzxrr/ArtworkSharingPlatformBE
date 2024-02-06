package com.github.khangzxrr.web.rest.errors;

public class RequestBidIsNotInValidStateException extends BadRequestAlertException {

    public RequestBidIsNotInValidStateException() {
        super(
            ErrorConstants.REQUEST_BID_IS_NOT_IN_CORRECT_STATE,
            "Request bid is not in correct state",
            "requestBid",
            "requestBidIsNotInCorrectState"
        );
    }
}
