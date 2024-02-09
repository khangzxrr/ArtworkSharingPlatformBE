package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.RequestProgressReportService;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.CreateRequestProgressReportDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/creator/requests")
public class RequestProgressResourceOfCreator {

    private final Logger log = LoggerFactory.getLogger(RequestProgressResourceOfCreator.class);

    private static final String ENTITY_NAME = "requestProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestProgressReportService requestProgressService;

    public RequestProgressResourceOfCreator(RequestProgressReportService requestProgressService) {
        this.requestProgressService = requestProgressService;
    }

    @PostMapping("{requestId}/request-progresses/reports")
    public ResponseEntity<RequestProgressDTO> createRequestProgressReport(
        @PathVariable(name = "requestId") long requestId,
        @Valid @RequestBody CreateRequestProgressReportDTO createRequestProgressReportDTO
    ) {
        log.info("Request create new request report progress for request id: {}, {}", requestId, createRequestProgressReportDTO);

        RequestProgressDTO requestProgressDTO = requestProgressService.create(requestId, createRequestProgressReportDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, requestProgressDTO.getId().toString()))
            .body(requestProgressDTO);
    }
}
