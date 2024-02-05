package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.RequestProgressRepository;
import com.github.khangzxrr.service.RequestProgressService;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.github.khangzxrr.domain.RequestProgress}.
 */
@RestController
@RequestMapping("/api/request-progresses")
public class RequestProgressResource {

    private final Logger log = LoggerFactory.getLogger(RequestProgressResource.class);

    private static final String ENTITY_NAME = "requestProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestProgressService requestProgressService;

    private final RequestProgressRepository requestProgressRepository;

    public RequestProgressResource(RequestProgressService requestProgressService, RequestProgressRepository requestProgressRepository) {
        this.requestProgressService = requestProgressService;
        this.requestProgressRepository = requestProgressRepository;
    }

    /**
     * {@code POST  /request-progresses} : Create a new requestProgress.
     *
     * @param requestProgressDTO the requestProgressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestProgressDTO, or with status {@code 400 (Bad Request)} if the requestProgress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestProgressDTO> createRequestProgress(@Valid @RequestBody RequestProgressDTO requestProgressDTO)
        throws URISyntaxException {
        log.debug("REST request to save RequestProgress : {}", requestProgressDTO);
        if (requestProgressDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestProgress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestProgressDTO result = requestProgressService.save(requestProgressDTO);
        return ResponseEntity
            .created(new URI("/api/request-progresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-progresses/:id} : Updates an existing requestProgress.
     *
     * @param id the id of the requestProgressDTO to save.
     * @param requestProgressDTO the requestProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestProgressDTO,
     * or with status {@code 400 (Bad Request)} if the requestProgressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestProgressDTO> updateRequestProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestProgressDTO requestProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestProgress : {}, {}", id, requestProgressDTO);
        if (requestProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestProgressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestProgressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestProgressDTO result = requestProgressService.update(requestProgressDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestProgressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-progresses/:id} : Partial updates given fields of an existing requestProgress, field will ignore if it is null
     *
     * @param id the id of the requestProgressDTO to save.
     * @param requestProgressDTO the requestProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestProgressDTO,
     * or with status {@code 400 (Bad Request)} if the requestProgressDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestProgressDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestProgressDTO> partialUpdateRequestProgress(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestProgressDTO requestProgressDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestProgress partially : {}, {}", id, requestProgressDTO);
        if (requestProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestProgressDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestProgressRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestProgressDTO> result = requestProgressService.partialUpdate(requestProgressDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestProgressDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /request-progresses} : get all the requestProgresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestProgresses in body.
     */
    @GetMapping("")
    public List<RequestProgressDTO> getAllRequestProgresses() {
        log.debug("REST request to get all RequestProgresses");
        return requestProgressService.findAll();
    }

    /**
     * {@code GET  /request-progresses/:id} : get the "id" requestProgress.
     *
     * @param id the id of the requestProgressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestProgressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestProgressDTO> getRequestProgress(@PathVariable("id") Long id) {
        log.debug("REST request to get RequestProgress : {}", id);
        Optional<RequestProgressDTO> requestProgressDTO = requestProgressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestProgressDTO);
    }

    /**
     * {@code DELETE  /request-progresses/:id} : delete the "id" requestProgress.
     *
     * @param id the id of the requestProgressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestProgress(@PathVariable("id") Long id) {
        log.debug("REST request to delete RequestProgress : {}", id);
        requestProgressService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
