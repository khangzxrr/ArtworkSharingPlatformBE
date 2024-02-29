package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.RequestProgressReportService;
import com.github.khangzxrr.service.RequestProgressService;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final RequestProgressReportService requestProgressReportService;
    private final RequestProgressService requestProgressService;

    public RequestProgressResourceOfAudience(
        RequestProgressReportService requestProgressReportService,
        RequestProgressService requestProgressService
    ) {
        this.requestProgressReportService = requestProgressReportService;
        this.requestProgressService = requestProgressService;
    }

    @GetMapping("{requestId}/request-progresses")
    public ResponseEntity<List<RequestProgressDTO>> getRequestProgresses(@PathVariable(name = "requestId") long requestId) {
        return ResponseEntity.ok().body(requestProgressService.findAllByRequestId(requestId));
    }
}
