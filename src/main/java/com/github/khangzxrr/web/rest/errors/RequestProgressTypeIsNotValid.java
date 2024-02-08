package com.github.khangzxrr.web.rest.errors;

public class RequestProgressTypeIsNotValid extends BadRequestAlertException {

    public RequestProgressTypeIsNotValid() {
        super(
            ErrorConstants.REQUEST_PROGRESS_TYPE_IS_NOT_VALID,
            "Request progress type is not valid",
            "requestProgress",
            "requestProgressTypeIsNotValid"
        );
    }
}
