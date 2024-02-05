package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.SellingBidRepository;
import com.github.khangzxrr.service.SellingBidService;
import com.github.khangzxrr.service.dto.SellingBidDTO;
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
 * REST controller for managing {@link com.github.khangzxrr.domain.SellingBid}.
 */
@RestController
@RequestMapping("/api/selling-bids")
public class SellingBidResource {

    private final Logger log = LoggerFactory.getLogger(SellingBidResource.class);

    private static final String ENTITY_NAME = "sellingBid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SellingBidService sellingBidService;

    private final SellingBidRepository sellingBidRepository;

    public SellingBidResource(SellingBidService sellingBidService, SellingBidRepository sellingBidRepository) {
        this.sellingBidService = sellingBidService;
        this.sellingBidRepository = sellingBidRepository;
    }

    /**
     * {@code POST  /selling-bids} : Create a new sellingBid.
     *
     * @param sellingBidDTO the sellingBidDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sellingBidDTO, or with status {@code 400 (Bad Request)} if the sellingBid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SellingBidDTO> createSellingBid(@Valid @RequestBody SellingBidDTO sellingBidDTO) throws URISyntaxException {
        log.debug("REST request to save SellingBid : {}", sellingBidDTO);
        if (sellingBidDTO.getId() != null) {
            throw new BadRequestAlertException("A new sellingBid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SellingBidDTO result = sellingBidService.save(sellingBidDTO);
        return ResponseEntity
            .created(new URI("/api/selling-bids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /selling-bids/:id} : Updates an existing sellingBid.
     *
     * @param id the id of the sellingBidDTO to save.
     * @param sellingBidDTO the sellingBidDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sellingBidDTO,
     * or with status {@code 400 (Bad Request)} if the sellingBidDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sellingBidDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SellingBidDTO> updateSellingBid(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SellingBidDTO sellingBidDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SellingBid : {}, {}", id, sellingBidDTO);
        if (sellingBidDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sellingBidDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sellingBidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SellingBidDTO result = sellingBidService.update(sellingBidDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sellingBidDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /selling-bids/:id} : Partial updates given fields of an existing sellingBid, field will ignore if it is null
     *
     * @param id the id of the sellingBidDTO to save.
     * @param sellingBidDTO the sellingBidDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sellingBidDTO,
     * or with status {@code 400 (Bad Request)} if the sellingBidDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sellingBidDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sellingBidDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SellingBidDTO> partialUpdateSellingBid(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SellingBidDTO sellingBidDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SellingBid partially : {}, {}", id, sellingBidDTO);
        if (sellingBidDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sellingBidDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sellingBidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SellingBidDTO> result = sellingBidService.partialUpdate(sellingBidDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sellingBidDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /selling-bids} : get all the sellingBids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sellingBids in body.
     */
    @GetMapping("")
    public List<SellingBidDTO> getAllSellingBids() {
        log.debug("REST request to get all SellingBids");
        return sellingBidService.findAll();
    }

    /**
     * {@code GET  /selling-bids/:id} : get the "id" sellingBid.
     *
     * @param id the id of the sellingBidDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sellingBidDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SellingBidDTO> getSellingBid(@PathVariable("id") Long id) {
        log.debug("REST request to get SellingBid : {}", id);
        Optional<SellingBidDTO> sellingBidDTO = sellingBidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sellingBidDTO);
    }

    /**
     * {@code DELETE  /selling-bids/:id} : delete the "id" sellingBid.
     *
     * @param id the id of the sellingBidDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSellingBid(@PathVariable("id") Long id) {
        log.debug("REST request to delete SellingBid : {}", id);
        sellingBidService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
