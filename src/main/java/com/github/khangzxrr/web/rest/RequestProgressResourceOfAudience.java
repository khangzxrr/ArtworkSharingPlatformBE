package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.RequestProgressService;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audience/requests")
public class RequestProgressResourceOfAudience {

    private final Logger log = LoggerFactory.getLogger(RequestProgressResourceOfAudience.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestProgressService requestProgressService;

    public RequestProgressResourceOfAudience(RequestProgressService requestProgressService) {
        this.requestProgressService = requestProgressService;
    }

    @GetMapping("{requestId}/request-progresses")
    public ResponseEntity<List<RequestProgressDTO>> getRequestProgresses(@PathVariable(name = "requestId") long requestId) {
        log.debug("get request progresses of request id " + requestId);
        return ResponseEntity.ok().body(requestProgressService.findAllByRequestId(requestId));
    }
}
