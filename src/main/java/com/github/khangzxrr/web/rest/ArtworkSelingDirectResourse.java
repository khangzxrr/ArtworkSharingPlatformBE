package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.ArtworkRepository;
import com.github.khangzxrr.service.ArtworkService;
import com.github.khangzxrr.service.dto.ArtworkDTO;
import com.github.khangzxrr.web.rest.errors.BadRequestAlertException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.ArtworkSelling}.
 */

@RestController
@RequestMapping("/api/artwork-Directsellings")
public class ArtworkSelingDirectResourse {

    private final Logger log = LoggerFactory.getLogger(ArtworkSellingResource.class);

    private static final String ENTITY_NAME = "artwork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkService artworkService;

    private final ArtworkRepository artworkRepository;

    public ArtworkSelingDirectResourse(ArtworkService artworkService, ArtworkRepository artworkRepository) {
        this.artworkService = artworkService;
        this.artworkRepository = artworkRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtworkDTO> getArtwork(@PathVariable("id") Long id) {
        log.debug("REST request to get Artwork : {}", id);
        Optional<ArtworkDTO> artworkDTO = artworkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkDTO);
    }

    @PostMapping("/{artworkId}/purchase")
    public ResponseEntity<String> purchaseArtwork(@PathVariable Long artworkId) {
        switch (artworkService.purchaseArtwork(artworkId)) {
            case 1:
                return ResponseEntity.ok("Artwork purchased successfully.");
            case 2:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to purchase artwork.");
            case 3:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot purchase your own artwork.");
            case 4:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Artwork have been sold.");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PutMapping("/creator/artworkSelling/{id}/sell")
    public ResponseEntity<ArtworkDTO> updateArtwork(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkDTO artworkDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Artwork : {}, {}", id, artworkDTO);
        if (artworkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        if (artworkRepository.existsArtworkSellingByArtworkId(id)) {
            throw new BadRequestAlertException("Artwork are being sold on shelves", ENTITY_NAME, "ArtworkSelingexist");
        }

        ArtworkDTO result = artworkService.updateSaleDirect(artworkDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/creator/artworkSelling/{id}/cancel")
    public ResponseEntity<Void> deleteArtwork(@PathVariable("id") Long id) {
        if (!artworkRepository.existsArtworkSellingByArtworkId(id)) {
            throw new BadRequestAlertException("Artwork Selling Doesn't exist", ENTITY_NAME, "ArtworkSeling_Doesnot_exist");
        }

        log.debug("REST request to delete Artwork : {}", id);
        artworkService.cancel(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
