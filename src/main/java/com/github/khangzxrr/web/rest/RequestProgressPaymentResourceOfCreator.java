package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.RequestPaymentService;
import com.github.khangzxrr.service.dto.requestProgressDto.RequestProgressPaymentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.RequestProgress}.
 */
@RestController
@RequestMapping("/api/creator/requests")
public class RequestProgressPaymentResourceOfCreator {

    private final Logger log = LoggerFactory.getLogger(RequestProgressPaymentResourceOfCreator.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestPaymentService requestPaymentService;

    public RequestProgressPaymentResourceOfCreator(RequestPaymentService requestPaymentService) {
        this.requestPaymentService = requestPaymentService;
    }

    @GetMapping("{requestId}/request-progresses/first-payment")
    public ResponseEntity<RequestProgressPaymentDTO> getFirstPayment(@PathVariable(name = "requestId") long requestId) {
        log.debug("get first-payment of request id {}", requestId);

        RequestProgressPaymentDTO requestProgressPaymentDTO = requestPaymentService.getFirstPayment(requestId);

        return ResponseEntity.ok().body(requestProgressPaymentDTO);
    }

    @GetMapping("{requestId}/request-progresses/second-payment")
    public ResponseEntity<RequestProgressPaymentDTO> getSecondPayment(@PathVariable(name = "requestId") long requestId) {
        log.debug("get second-payment of request id {}", requestId);

        RequestProgressPaymentDTO requestProgressPaymentDTO = requestPaymentService.getSecondPayment(requestId);

        return ResponseEntity.ok().body(requestProgressPaymentDTO);
    }
}
