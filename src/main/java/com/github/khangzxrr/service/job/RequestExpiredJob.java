package com.github.khangzxrr.service.job;

import com.github.khangzxrr.service.RequestService;
import jakarta.transaction.Transactional;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class RequestExpiredJob extends QuartzJobBean {

    private final Logger log = LoggerFactory.getLogger(RequestExpiredJob.class);

    public static final String name = "RequestExpiredJob";
    public static final String group = "RequestGroup";

    public static final String requestIdKey = "requestIdKey";

    @Autowired
    private RequestService requestService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // long requestId = context.getJobDetail().getJobDataMap().getLong(requestIdKey);

        // Optional<Request> requestOptional = requestRepository.findById(requestId);

        // if (!requestOptional.isPresent()) {
        //     log.info("request is not exist with id: " + requestId);
        //     return;
        // }

        // requestOptional.get().setStatus(RequestStatus.FAILED);
        // requestRepository.save(requestOptional.get());

        log.info("check expired requests...");

        requestService.clearExpiredRequest();
    }

    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }
}
