package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.RequestBidService;
import com.github.khangzxrr.service.dto.CreateRequestBidDTO;
import com.github.khangzxrr.service.dto.RequestBidDTO;
import com.github.khangzxrr.service.dto.UpdateRequestBidDTO;
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
 * REST controller for managing {@link com.github.khangzxrr.domain.RequestBid}.
 */
@RestController
@RequestMapping("/api/creator/requests")
public class RequestBidResourceOfCreator {

    private final Logger log = LoggerFactory.getLogger(RequestBidResourceOfCreator.class);

    private static final String ENTITY_NAME = "requestBid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestBidService requestBidService;

    public RequestBidResourceOfCreator(RequestBidService requestBidService) {
        this.requestBidService = requestBidService;
    }

    /**
     * {@code POST  /request-bids} : Create a new requestBid.
     *
     * @param requestBidDTO the requestBidDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestBidDTO, or with status {@code 400 (Bad Request)} if the requestBid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/{requestId}/request-bids")
    public ResponseEntity<RequestBidDTO> createRequestBid(
        @PathVariable(name = "requestId") Long requestId,
        @Valid @RequestBody CreateRequestBidDTO createRequestBidDTO
    ) throws URISyntaxException {
        log.debug("REST request to save RequestBid : {}", createRequestBidDTO);

        RequestBidDTO result = requestBidService.placeBidOnRequest(requestId, createRequestBidDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-bids/:id} : Updates an existing requestBid.
     *
     * @param id the id of the requestBidDTO to save.
     * @param requestBidDTO the requestBidDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestBidDTO,
     * or with status {@code 400 (Bad Request)} if the requestBidDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestBidDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{requestId}/request-bids/{requestBidId}")
    public ResponseEntity<RequestBidDTO> updateRequestBid(
        @PathVariable(name = "requestId") Long requestId,
        @PathVariable(value = "requestBidId") final Long requestBidId,
        @Valid @RequestBody UpdateRequestBidDTO updateRequestBidDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestBid : {}, {}, {}", requestId, requestBidId, updateRequestBidDTO);

        RequestBidDTO result = requestBidService.updateRequestBid(requestId, requestBidId, updateRequestBidDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /request-bids} : get all the requestBids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestBids in body.
     */
    @GetMapping("/{requestId}/request-bids")
    public ResponseEntity<List<RequestBidDTO>> getAllRequestBids(
        @PathVariable("requestId") Long requestId,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all RequestBids");

        Page<RequestBidDTO> page = requestBidService.findAllRequestBid(requestId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /request-bids/:id} : get the "id" requestBid.
     *
     * @param id the id of the requestBidDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestBidDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("{requestId}/request-bids/{requestBidId}")
    public ResponseEntity<RequestBidDTO> getRequestBid(
        @PathVariable("requestId") Long requestId,
        @PathVariable("requestBidId") Long requestBidId
    ) {
        log.debug("REST request to get RequestBid : {}, {}", requestId, requestBidId);

        Optional<RequestBidDTO> requestBidDTO = requestBidService.findOneRequestBid(requestId, requestBidId);
        return ResponseUtil.wrapOrNotFound(requestBidDTO);
    }

    /**
     * {@code DELETE  /request-bids/:id} : delete the "id" requestBid.
     *
     * @param id the id of the requestBidDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("request-bids/{requestBidId}")
    public ResponseEntity<Void> deleteRequestBid(
        @PathVariable("requestId") Long requestId,
        @PathVariable("requestBidId") Long requestBidId
    ) {
        log.debug("REST request to delete RequestBid : {}", requestBidId);

        requestBidService.deleteRequestBid(requestId, requestBidId);

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, requestBidId.toString()))
            .build();
    }
}
