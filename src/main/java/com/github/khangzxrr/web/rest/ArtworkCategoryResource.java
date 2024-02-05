package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.ArtworkCategoryRepository;
import com.github.khangzxrr.service.ArtworkCategoryService;
import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
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
 * REST controller for managing {@link com.github.khangzxrr.domain.ArtworkCategory}.
 */
@RestController
@RequestMapping("/api/artwork-categories")
public class ArtworkCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkCategoryResource.class);

    private static final String ENTITY_NAME = "artworkCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkCategoryService artworkCategoryService;

    private final ArtworkCategoryRepository artworkCategoryRepository;

    public ArtworkCategoryResource(ArtworkCategoryService artworkCategoryService, ArtworkCategoryRepository artworkCategoryRepository) {
        this.artworkCategoryService = artworkCategoryService;
        this.artworkCategoryRepository = artworkCategoryRepository;
    }

    /**
     * {@code POST  /artwork-categories} : Create a new artworkCategory.
     *
     * @param artworkCategoryDTO the artworkCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artworkCategoryDTO, or with status {@code 400 (Bad Request)} if the artworkCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArtworkCategoryDTO> createArtworkCategory(@RequestBody ArtworkCategoryDTO artworkCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save ArtworkCategory : {}", artworkCategoryDTO);
        if (artworkCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new artworkCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtworkCategoryDTO result = artworkCategoryService.save(artworkCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/artwork-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artwork-categories/:id} : Updates an existing artworkCategory.
     *
     * @param id the id of the artworkCategoryDTO to save.
     * @param artworkCategoryDTO the artworkCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the artworkCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artworkCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArtworkCategoryDTO> updateArtworkCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkCategoryDTO artworkCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtworkCategory : {}, {}", id, artworkCategoryDTO);
        if (artworkCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtworkCategoryDTO result = artworkCategoryService.update(artworkCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artwork-categories/:id} : Partial updates given fields of an existing artworkCategory, field will ignore if it is null
     *
     * @param id the id of the artworkCategoryDTO to save.
     * @param artworkCategoryDTO the artworkCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the artworkCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artworkCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artworkCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtworkCategoryDTO> partialUpdateArtworkCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkCategoryDTO artworkCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtworkCategory partially : {}, {}", id, artworkCategoryDTO);
        if (artworkCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtworkCategoryDTO> result = artworkCategoryService.partialUpdate(artworkCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artwork-categories} : get all the artworkCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artworkCategories in body.
     */
    @GetMapping("")
    public List<ArtworkCategoryDTO> getAllArtworkCategories() {
        log.debug("REST request to get all ArtworkCategories");
        return artworkCategoryService.findAll();
    }

    /**
     * {@code GET  /artwork-categories/:id} : get the "id" artworkCategory.
     *
     * @param id the id of the artworkCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artworkCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtworkCategoryDTO> getArtworkCategory(@PathVariable("id") Long id) {
        log.debug("REST request to get ArtworkCategory : {}", id);
        Optional<ArtworkCategoryDTO> artworkCategoryDTO = artworkCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkCategoryDTO);
    }

    /**
     * {@code DELETE  /artwork-categories/:id} : delete the "id" artworkCategory.
     *
     * @param id the id of the artworkCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtworkCategory(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArtworkCategory : {}", id);
        artworkCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
