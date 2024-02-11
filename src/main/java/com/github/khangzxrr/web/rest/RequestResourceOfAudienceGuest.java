package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.service.RequestService;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.mapper.RequestMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.Request}.
 */
@RestController
@RequestMapping("/api/guest/requests")
public class RequestResourceOfAudienceGuest {

    private final Logger log = LoggerFactory.getLogger(RequestResourceOfAudience.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    public RequestResourceOfAudienceGuest(RequestService requestService, RequestMapper requestMapper) {
        this.requestService = requestService;
        this.requestMapper = requestMapper;
    }

    /**
     * {@code GET  /requests} : get all the requests belong to audience
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requests in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RequestDTO>> getAllRequests(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Requests belong to audience");

        Page<RequestDTO> page = requestService.getAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requests/:id} : get the "id" request.
     *
     * @param id the id of the requestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    //required transactional because mapstruct require single transaction to map lazy entities
    @Transactional(readOnly = true)
    public ResponseEntity<RequestDTO> getRequest(@PathVariable("id") Long id) {
        log.debug("REST request to get Request : {}", id);
        Optional<Request> request = requestService.getOne(id);

        return ResponseUtil.wrapOrNotFound(request.map(requestMapper::toDto));
    }
}
