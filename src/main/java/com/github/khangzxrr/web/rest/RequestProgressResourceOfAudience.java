package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.RequestProgressReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/audience/requests")
public class RequestProgressResourceOfAudience {

    private final Logger log = LoggerFactory.getLogger(RequestProgressResourceOfAudience.class);

    private static final String ENTITY_NAME = "requestProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestProgressReportService requestProgressService;

    public RequestProgressResourceOfAudience(RequestProgressReportService requestProgressService) {
        this.requestProgressService = requestProgressService;
    }

    @PostMapping("{requestId}/request-progresses/{requestProgressId}/reports/reject")
    public ResponseEntity<Void> rejectReport(
        @PathVariable(name = "requestId") long requestId,
        @PathVariable(name = "requestProgressId") long requestProgressId
    ) {
        log.info("Reject report with request id: {}", requestId);

        requestProgressService.reject(requestId, requestProgressId);

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, Long.toString(requestId)))
            .build();
    }

    @PostMapping("{requestId}/request-progresses/{requestProgressId}/reports/accept")
    public ResponseEntity<Void> acceptReport(
        @PathVariable(name = "requestId") long requestId,
        @PathVariable(name = "requestProgressId") long requestProgressId
    ) {
        log.info("Accept report with request id: {}", requestId);

        requestProgressService.accept(requestId, requestProgressId);

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, Long.toString(requestId)))
            .build();
    }
}
