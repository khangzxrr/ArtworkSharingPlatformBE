package com.github.khangzxrr.web.rest.errors;

public class RequestIsNotOnCorrectState extends BadRequestAlertException {

    public RequestIsNotOnCorrectState() {
        super(
            ErrorConstants.REQUEST_IS_NOT_ON_CORRECT_STATE,
            "Request is not on correct state to perform action",
            "request",
            "RequestIsNotOnCorrectState"
        );
    }
}
