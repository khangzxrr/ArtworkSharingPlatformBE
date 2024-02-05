package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.ArtworkComplainRepository;
import com.github.khangzxrr.service.ArtworkComplainService;
import com.github.khangzxrr.service.dto.ArtworkComplainDTO;
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
 * REST controller for managing {@link com.github.khangzxrr.domain.ArtworkComplain}.
 */
//@RestController
//@RequestMapping("/api/artwork-complains")
public class ArtworkComplainResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkComplainResource.class);

    private static final String ENTITY_NAME = "artworkComplain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkComplainService artworkComplainService;

    private final ArtworkComplainRepository artworkComplainRepository;

    public ArtworkComplainResource(ArtworkComplainService artworkComplainService, ArtworkComplainRepository artworkComplainRepository) {
        this.artworkComplainService = artworkComplainService;
        this.artworkComplainRepository = artworkComplainRepository;
    }

    /**
     * {@code POST  /artwork-complains} : Create a new artworkComplain.
     *
     * @param artworkComplainDTO the artworkComplainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artworkComplainDTO, or with status {@code 400 (Bad Request)} if the artworkComplain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArtworkComplainDTO> createArtworkComplain(@RequestBody ArtworkComplainDTO artworkComplainDTO)
        throws URISyntaxException {
        log.debug("REST request to save ArtworkComplain : {}", artworkComplainDTO);
        if (artworkComplainDTO.getId() != null) {
            throw new BadRequestAlertException("A new artworkComplain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtworkComplainDTO result = artworkComplainService.save(artworkComplainDTO);
        return ResponseEntity
            .created(new URI("/api/artwork-complains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artwork-complains/:id} : Updates an existing artworkComplain.
     *
     * @param id the id of the artworkComplainDTO to save.
     * @param artworkComplainDTO the artworkComplainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkComplainDTO,
     * or with status {@code 400 (Bad Request)} if the artworkComplainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artworkComplainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArtworkComplainDTO> updateArtworkComplain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkComplainDTO artworkComplainDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtworkComplain : {}, {}", id, artworkComplainDTO);
        if (artworkComplainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkComplainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkComplainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtworkComplainDTO result = artworkComplainService.update(artworkComplainDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkComplainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artwork-complains/:id} : Partial updates given fields of an existing artworkComplain, field will ignore if it is null
     *
     * @param id the id of the artworkComplainDTO to save.
     * @param artworkComplainDTO the artworkComplainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkComplainDTO,
     * or with status {@code 400 (Bad Request)} if the artworkComplainDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artworkComplainDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artworkComplainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtworkComplainDTO> partialUpdateArtworkComplain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkComplainDTO artworkComplainDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtworkComplain partially : {}, {}", id, artworkComplainDTO);
        if (artworkComplainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkComplainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkComplainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtworkComplainDTO> result = artworkComplainService.partialUpdate(artworkComplainDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkComplainDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artwork-complains} : get all the artworkComplains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artworkComplains in body.
     */
    @GetMapping("")
    public List<ArtworkComplainDTO> getAllArtworkComplains() {
        log.debug("REST request to get all ArtworkComplains");
        return artworkComplainService.findAll();
    }

    /**
     * {@code GET  /artwork-complains/:id} : get the "id" artworkComplain.
     *
     * @param id the id of the artworkComplainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artworkComplainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtworkComplainDTO> getArtworkComplain(@PathVariable("id") Long id) {
        log.debug("REST request to get ArtworkComplain : {}", id);
        Optional<ArtworkComplainDTO> artworkComplainDTO = artworkComplainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkComplainDTO);
    }

    /**
     * {@code DELETE  /artwork-complains/:id} : delete the "id" artworkComplain.
     *
     * @param id the id of the artworkComplainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtworkComplain(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArtworkComplain : {}", id);
        artworkComplainService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
