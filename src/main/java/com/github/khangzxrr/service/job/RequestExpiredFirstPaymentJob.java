package com.github.khangzxrr.service.job;

import com.github.khangzxrr.service.RequestService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RequestExpiredFirstPaymentJob extends QuartzJobBean {

    public static final String name = "requestExpiredFirstPaymentJob";
    public static final String group = "RequestGroup";

    @Autowired
    private RequestService requestService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        requestService.clearExpiredRequestFirstPayment();
    }

    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }
}
