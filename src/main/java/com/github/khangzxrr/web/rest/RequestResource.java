package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.service.RequestService;
import com.github.khangzxrr.service.dto.CreateRequestDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.dto.UpdateRequestDTO;
import com.github.khangzxrr.service.mapper.RequestMapper;
import jakarta.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.Request}.
 */
@RestController
@RequestMapping("/api/audience/requests")
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    public RequestResource(RequestService requestService, RequestMapper requestMapper) {
        this.requestService = requestService;
        this.requestMapper = requestMapper;
    }

    /**
     * {@code POST  /requests} : Create a new request of audience
     *
     * @param requestDTO the requestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestDTO, or with status {@code 400 (Bad Request)} if the request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestDTO> createRequest(@Valid @RequestBody CreateRequestDTO createRequestDTO) throws URISyntaxException {
        log.debug("REST request to save Request : {}", createRequestDTO);

        RequestDTO result = requestService.create(createRequestDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requests/:id} : Updates an existing request.
     *
     * @param id the id of the requestDTO to save.
     * @param requestDTO the requestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDTO,
     * or with status {@code 400 (Bad Request)} if the requestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestDTO> updateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UpdateRequestDTO updateRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Request : {}, {}", id, updateRequestDTO);

        RequestDTO result = requestService.update(id, updateRequestDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
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

        Page<RequestDTO> page = requestService.findAll(pageable);
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
    public ResponseEntity<RequestDTO> getRequest(@PathVariable("id") Long id) {
        log.debug("REST request to get Request : {}", id);
        Optional<Request> request = requestService.getRequestByIdAndBelongToCurrentUser(id);

        Optional<RequestDTO> requestDTO = request.isPresent() ? Optional.of(requestMapper.toDto(request.get())) : Optional.empty();

        return ResponseUtil.wrapOrNotFound(requestDTO);
    }

    /**
     * {@code DELETE  /requests/:id} : delete the "id" request.
     *
     * @param id the id of the requestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable("id") Long id) {
        log.debug("REST request to delete Request : {}", id);
        requestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
