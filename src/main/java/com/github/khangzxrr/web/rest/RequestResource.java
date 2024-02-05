package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.RequestRepository;
import com.github.khangzxrr.service.RequestService;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.Request}.
 */
@RestController
@RequestMapping("/api/requests")
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestService requestService;

    private final RequestRepository requestRepository;

    public RequestResource(RequestService requestService, RequestRepository requestRepository) {
        this.requestService = requestService;
        this.requestRepository = requestRepository;
    }

    /**
     * {@code POST  /requests} : Create a new request.
     *
     * @param requestDTO the requestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestDTO, or with status {@code 400 (Bad Request)} if the request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO requestDTO) throws URISyntaxException {
        log.debug("REST request to save Request : {}", requestDTO);
        if (requestDTO.getId() != null) {
            throw new BadRequestAlertException("A new request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestDTO result = requestService.save(requestDTO);
        return ResponseEntity
            .created(new URI("/api/requests/" + result.getId()))
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
        @RequestBody RequestDTO requestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Request : {}, {}", id, requestDTO);
        if (requestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestDTO result = requestService.update(requestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /requests/:id} : Partial updates given fields of an existing request, field will ignore if it is null
     *
     * @param id the id of the requestDTO to save.
     * @param requestDTO the requestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDTO,
     * or with status {@code 400 (Bad Request)} if the requestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestDTO> partialUpdateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestDTO requestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Request partially : {}, {}", id, requestDTO);
        if (requestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestDTO> result = requestService.partialUpdate(requestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /requests} : get all the requests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requests in body.
     */
    @GetMapping("")
    public List<RequestDTO> getAllRequests() {
        log.debug("REST request to get all Requests");
        return requestService.findAll();
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
        Optional<RequestDTO> requestDTO = requestService.findOne(id);
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
