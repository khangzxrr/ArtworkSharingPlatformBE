package com.github.khangzxrr.web.rest.errors;

public class RequestIsOwnedByUserException extends BadRequestAlertException {

    public RequestIsOwnedByUserException() {
        super(ErrorConstants.REQUEST_IS_OWNED_BY_USER, "Request is owned by user", "request", "requestIsOwnedByUser");
    }
}
