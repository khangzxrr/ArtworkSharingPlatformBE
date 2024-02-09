package com.github.khangzxrr.web.rest.errors;

public class RequestProgressReportIsExistException extends BadRequestAlertException {

    public RequestProgressReportIsExistException() {
        super(
            "Request progress report type is exist, please use update request progress API",
            "requestProgress",
            "requestProgressTypeIsExist"
        );
    }
}
