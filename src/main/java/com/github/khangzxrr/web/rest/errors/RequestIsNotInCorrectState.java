package com.github.khangzxrr.web.rest.errors;

public class RequestIsNotInCorrectState extends BadRequestAlertException {

    public RequestIsNotInCorrectState() {
        super(
            ErrorConstants.REQUEST_IS_NOT_IN_CORRECT_STATE,
            "Request is not in correct state to perform action",
            "request",
            "RequestIsNotOnCorrectState"
        );
    }
}
