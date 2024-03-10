package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.ArtworkService;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.CreateArtworkDTO;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.Artwork}.
 */
@RestController
@RequestMapping({ "/api/creator/artworks" })
public class ArtworkResourceOfCreator {

    private final Logger log = LoggerFactory.getLogger(ArtworkResourceOfCreator.class);

    private static final String ENTITY_NAME = "artwork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkService artworkService;

    public ArtworkResourceOfCreator(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    /**
     * {@code POST  /artworks} : Create a new artwork.
     *
     * @param artworkDTO the artworkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new artworkDTO, or with status {@code 400 (Bad Request)} if
     *         the artwork has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArtworkDTO> createArtwork(@Valid @RequestBody CreateArtworkDTO createArtworkDTO) throws URISyntaxException {
        log.debug("REST request to save Artwork : {}", createArtworkDTO);

        ArtworkDTO result = artworkService.save(createArtworkDTO);
        return ResponseEntity
            .created(new URI("/api/artworks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code DELETE  /artworks/:id} : delete the "id" artwork.
     *
     * @param id the id of the artworkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtwork(@PathVariable("id") Long id) {
        log.debug("REST request to delete Artwork : {}", id);
        artworkService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
