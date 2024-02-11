package com.github.khangzxrr.web.rest.errors;

public class NotAllRequestProgressReportFinishedException extends BadRequestAlertException {

    public NotAllRequestProgressReportFinishedException() {
        super("Not all request progress report finished", "requestProgress", "notAllRequestProgressReportFinished");
    }
}
