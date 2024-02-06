package com.github.khangzxrr.web.rest.errors;

public class RequestIsBelongToCurrentUser extends BadRequestAlertException {

    public RequestIsBelongToCurrentUser() {
        super(
            ErrorConstants.REQUEST_IS_BELONG_TO_CURRENT_USER,
            "Request is belong to current user, cannot perform action",
            "request",
            "requestIsBelongToCurrentUser"
        );
    }
}
