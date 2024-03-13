package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.ArtworkSellingService;
import com.github.khangzxrr.service.dto.SellingBidDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.SellingBid}.
 */
@RestController
@RequestMapping({ "/api/audience/artworks", "/api/creator/artworks" })
public class SellingBidResource {

    private final Logger log = LoggerFactory.getLogger(SellingBidResource.class);

    private static final String ENTITY_NAME = "sellingBid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkSellingService artworkSellingService;

    public SellingBidResource(ArtworkSellingService artworkSellingService) {
        this.artworkSellingService = artworkSellingService;
    }

    @GetMapping("{artworkId}/sellings/{id}/bids")
    public ResponseEntity<List<SellingBidDTO>> getAllBids(
        @PathVariable(name = "artworkId") Long artworkId,
        @PathVariable(name = "id") Long id
    ) {
        log.debug("REST request to get all Bids : {}", artworkId, id);

        List<SellingBidDTO> list = artworkSellingService.getAllBids(id, artworkId);

        return ResponseEntity.ok().body(list);
    }

    @PostMapping("{artworkId}/sellings/{id}/bids")
    public ResponseEntity<SellingBidDTO> placeBid(
        @PathVariable(name = "artworkId") Long artworkId,
        @PathVariable(name = "id") Long id,
        @Valid @RequestBody SellingBidDTO sellingBidDTO
    ) {
        log.debug(" place bid request : {}, {}", artworkId, id);

        SellingBidDTO result = artworkSellingService.placeBid(id, artworkId, sellingBidDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
