package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.ArtworkAssetRepository;
import com.github.khangzxrr.service.ArtworkAssetService;
import com.github.khangzxrr.service.dto.ArtworkAssetDTO;
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
 * REST controller for managing {@link com.github.khangzxrr.domain.ArtworkAsset}.
 */
//@RestController
//@RequestMapping("/api/artwork-assets")
public class ArtworkAssetResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkAssetResource.class);

    private static final String ENTITY_NAME = "artworkAsset";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkAssetService artworkAssetService;

    private final ArtworkAssetRepository artworkAssetRepository;

    public ArtworkAssetResource(ArtworkAssetService artworkAssetService, ArtworkAssetRepository artworkAssetRepository) {
        this.artworkAssetService = artworkAssetService;
        this.artworkAssetRepository = artworkAssetRepository;
    }

    /**
     * {@code POST  /artwork-assets} : Create a new artworkAsset.
     *
     * @param artworkAssetDTO the artworkAssetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artworkAssetDTO, or with status {@code 400 (Bad Request)} if the artworkAsset has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArtworkAssetDTO> createArtworkAsset(@RequestBody ArtworkAssetDTO artworkAssetDTO) throws URISyntaxException {
        log.debug("REST request to save ArtworkAsset : {}", artworkAssetDTO);
        if (artworkAssetDTO.getId() != null) {
            throw new BadRequestAlertException("A new artworkAsset cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtworkAssetDTO result = artworkAssetService.save(artworkAssetDTO);
        return ResponseEntity
            .created(new URI("/api/artwork-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artwork-assets/:id} : Updates an existing artworkAsset.
     *
     * @param id the id of the artworkAssetDTO to save.
     * @param artworkAssetDTO the artworkAssetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkAssetDTO,
     * or with status {@code 400 (Bad Request)} if the artworkAssetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artworkAssetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArtworkAssetDTO> updateArtworkAsset(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkAssetDTO artworkAssetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtworkAsset : {}, {}", id, artworkAssetDTO);
        if (artworkAssetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkAssetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkAssetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtworkAssetDTO result = artworkAssetService.update(artworkAssetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkAssetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artwork-assets/:id} : Partial updates given fields of an existing artworkAsset, field will ignore if it is null
     *
     * @param id the id of the artworkAssetDTO to save.
     * @param artworkAssetDTO the artworkAssetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkAssetDTO,
     * or with status {@code 400 (Bad Request)} if the artworkAssetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artworkAssetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artworkAssetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtworkAssetDTO> partialUpdateArtworkAsset(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkAssetDTO artworkAssetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtworkAsset partially : {}, {}", id, artworkAssetDTO);
        if (artworkAssetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkAssetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkAssetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtworkAssetDTO> result = artworkAssetService.partialUpdate(artworkAssetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkAssetDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artwork-assets} : get all the artworkAssets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artworkAssets in body.
     */
    @GetMapping("")
    public List<ArtworkAssetDTO> getAllArtworkAssets() {
        log.debug("REST request to get all ArtworkAssets");
        return artworkAssetService.findAll();
    }

    /**
     * {@code GET  /artwork-assets/:id} : get the "id" artworkAsset.
     *
     * @param id the id of the artworkAssetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artworkAssetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtworkAssetDTO> getArtworkAsset(@PathVariable("id") Long id) {
        log.debug("REST request to get ArtworkAsset : {}", id);
        Optional<ArtworkAssetDTO> artworkAssetDTO = artworkAssetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkAssetDTO);
    }

    /**
     * {@code DELETE  /artwork-assets/:id} : delete the "id" artworkAsset.
     *
     * @param id the id of the artworkAssetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtworkAsset(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArtworkAsset : {}", id);
        artworkAssetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
