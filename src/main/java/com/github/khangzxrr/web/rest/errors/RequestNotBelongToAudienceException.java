package com.github.khangzxrr.web.rest.errors;

public class RequestNotBelongToAudienceException extends BadRequestAlertException {

    public RequestNotBelongToAudienceException() {
        super(ErrorConstants.REQUEST_NOT_BELONG_TO_AUDIENCE, "Request is not belong to audience", "request", "requestNotBelongToAudience");
    }
}
