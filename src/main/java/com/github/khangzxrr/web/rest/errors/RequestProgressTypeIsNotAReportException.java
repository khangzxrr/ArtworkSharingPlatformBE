package com.github.khangzxrr.web.rest.errors;

public class RequestProgressTypeIsNotAReportException extends BadRequestAlertException {

    public RequestProgressTypeIsNotAReportException() {
        super(
            ErrorConstants.REQUEST_PROGRESS_TYPE_NOT_A_REPORT,
            "Request progress type is not a report",
            "requestProgress",
            "requestProgressTypeIsNotAReport"
        );
    }
}
