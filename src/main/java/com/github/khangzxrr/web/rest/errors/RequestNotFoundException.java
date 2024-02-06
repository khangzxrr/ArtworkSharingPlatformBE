package com.github.khangzxrr.web.rest.errors;

public class RequestNotFoundException extends BadRequestAlertException {

    public RequestNotFoundException() {
        super(ErrorConstants.REQUEST_NOT_FOUND, "Request is not found", "request", "requestNotFound");
    }
}
