package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.RequestAttachmentRepository;
import com.github.khangzxrr.service.RequestAttachmentService;
import com.github.khangzxrr.service.dto.RequestAttachmentDTO;
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
 * REST controller for managing {@link com.github.khangzxrr.domain.RequestAttachment}.
 */
@RestController
@RequestMapping("/api/request-attachments")
public class RequestAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(RequestAttachmentResource.class);

    private static final String ENTITY_NAME = "requestAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestAttachmentService requestAttachmentService;

    private final RequestAttachmentRepository requestAttachmentRepository;

    public RequestAttachmentResource(
        RequestAttachmentService requestAttachmentService,
        RequestAttachmentRepository requestAttachmentRepository
    ) {
        this.requestAttachmentService = requestAttachmentService;
        this.requestAttachmentRepository = requestAttachmentRepository;
    }

    /**
     * {@code POST  /request-attachments} : Create a new requestAttachment.
     *
     * @param requestAttachmentDTO the requestAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestAttachmentDTO, or with status {@code 400 (Bad Request)} if the requestAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestAttachmentDTO> createRequestAttachment(@RequestBody RequestAttachmentDTO requestAttachmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save RequestAttachment : {}", requestAttachmentDTO);
        if (requestAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestAttachmentDTO result = requestAttachmentService.save(requestAttachmentDTO);
        return ResponseEntity
            .created(new URI("/api/request-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-attachments/:id} : Updates an existing requestAttachment.
     *
     * @param id the id of the requestAttachmentDTO to save.
     * @param requestAttachmentDTO the requestAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the requestAttachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestAttachmentDTO> updateRequestAttachment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestAttachmentDTO requestAttachmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestAttachment : {}, {}", id, requestAttachmentDTO);
        if (requestAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestAttachmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestAttachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestAttachmentDTO result = requestAttachmentService.update(requestAttachmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-attachments/:id} : Partial updates given fields of an existing requestAttachment, field will ignore if it is null
     *
     * @param id the id of the requestAttachmentDTO to save.
     * @param requestAttachmentDTO the requestAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the requestAttachmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestAttachmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestAttachmentDTO> partialUpdateRequestAttachment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestAttachmentDTO requestAttachmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestAttachment partially : {}, {}", id, requestAttachmentDTO);
        if (requestAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestAttachmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestAttachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestAttachmentDTO> result = requestAttachmentService.partialUpdate(requestAttachmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestAttachmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /request-attachments} : get all the requestAttachments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestAttachments in body.
     */
    @GetMapping("")
    public List<RequestAttachmentDTO> getAllRequestAttachments() {
        log.debug("REST request to get all RequestAttachments");
        return requestAttachmentService.findAll();
    }

    /**
     * {@code GET  /request-attachments/:id} : get the "id" requestAttachment.
     *
     * @param id the id of the requestAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestAttachmentDTO> getRequestAttachment(@PathVariable("id") Long id) {
        log.debug("REST request to get RequestAttachment : {}", id);
        Optional<RequestAttachmentDTO> requestAttachmentDTO = requestAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestAttachmentDTO);
    }

    /**
     * {@code DELETE  /request-attachments/:id} : delete the "id" requestAttachment.
     *
     * @param id the id of the requestAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestAttachment(@PathVariable("id") Long id) {
        log.debug("REST request to delete RequestAttachment : {}", id);
        requestAttachmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
