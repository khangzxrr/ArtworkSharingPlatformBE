package com.github.khangzxrr.web.rest.errors;

public class RequestProgressIsNotExistException extends BadRequestAlertException {

    public RequestProgressIsNotExistException() {
        super("request progress is not exist", "requestProgress", "requestProgressIsNotExist");
    }
}
