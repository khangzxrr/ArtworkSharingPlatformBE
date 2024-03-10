package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.RequestBidService;
import com.github.khangzxrr.service.dto.RequestBidDTO;
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
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.RequestBid}.
 */
@RestController
@RequestMapping("/api/audience/requests")
public class RequestBidResourceOfAudience {

    private final Logger log = LoggerFactory.getLogger(RequestBidResourceOfCreator.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestBidService requestBidService;

    public RequestBidResourceOfAudience(RequestBidService requestBidService) {
        this.requestBidService = requestBidService;
    }

    /**
     * {@code GET  /request-bids} : get all the requestBids.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of requestBids in body.
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

    @GetMapping("{requestId}/request-bids/choosed")
    public ResponseEntity<RequestBidDTO> getChoosedRequestBid(@PathVariable("requestId") Long requestId) {
        log.debug("REST request to get choosed request bid : {}", requestId);

        Optional<RequestBidDTO> requestBidDTO = requestBidService.findChoosed(requestId);

        return ResponseUtil.wrapOrNotFound(requestBidDTO);
    }

    /**
     * {@code GET  /request-bids/:id} : get the "id" requestBid.
     *
     * @param id the id of the requestBidDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the requestBidDTO, or with status {@code 404 (Not Found)}.
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
}
