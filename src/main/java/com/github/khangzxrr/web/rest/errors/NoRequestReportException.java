package com.github.khangzxrr.web.rest.errors;

public class NoRequestReportException extends BadRequestAlertException {

    public NoRequestReportException() {
        super("No request report has been uploaded", "requestProgress", "noRequestReportException");
    }
}
