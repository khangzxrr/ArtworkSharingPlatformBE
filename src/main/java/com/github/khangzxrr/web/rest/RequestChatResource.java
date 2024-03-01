package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.RequestChatService;
import com.github.khangzxrr.service.dto.requestChatDTOs.CreateRequestChatDTO;
import com.github.khangzxrr.service.dto.requestChatDTOs.RequestChatDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.Request}.
 */
@RestController
@RequestMapping({ "/api/audience/requests", "/api/creator/requests" })
public class RequestChatResource {

    private final Logger log = LoggerFactory.getLogger(RequestChatResource.class);

    private static final String ENTITY_NAME = "requestChat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestChatService requestChatService;

    public RequestChatResource(RequestChatService requestChatService) {
        this.requestChatService = requestChatService;
    }

    /**
     * {@code GET  /requests} : get all the requests belong to audience
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requests in body.
     */
    @GetMapping("{requestId}/chats")
    public ResponseEntity<List<RequestChatDTO>> getAllChats(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam final long afterId,
        @PathVariable(name = "requestId") final long requestId
    ) {
        log.debug("REST request to get a page of Requests belong to audience");

        return ResponseEntity.ok().body(requestChatService.getAllAfterId(requestId, afterId));
    }

    @PostMapping("{requestId}/chats")
    public ResponseEntity<RequestChatDTO> createChat(
        @PathVariable(name = "requestId") final long requestId,
        @Valid @RequestBody CreateRequestChatDTO createRequestChatDTO
    ) {
        RequestChatDTO createdRequestChatDTO = requestChatService.create(requestId, createRequestChatDTO);

        return ResponseEntity.ok().body(createdRequestChatDTO);
    }
}
