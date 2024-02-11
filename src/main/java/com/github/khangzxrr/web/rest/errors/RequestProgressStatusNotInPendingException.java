package com.github.khangzxrr.web.rest.errors;

public class RequestProgressStatusNotInPendingException extends BadRequestAlertException {

    public RequestProgressStatusNotInPendingException() {
        super("Request progress status is not in pending state", "requestProgress", "requestProgressStatusIsNotInPendingState");
    }
}
