package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.ArtworkSellingRepository;
import com.github.khangzxrr.service.ArtworkSellingService;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import jakarta.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.github.khangzxrr.domain.ArtworkSelling}.
 */
@RestController
@RequestMapping({ "/api/audience/artworks", "/api/creator/artworks" })
public class ArtworkSellingResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkSellingResource.class);

    private static final String ENTITY_NAME = "artworkSelling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkSellingService artworkSellingService;

    public ArtworkSellingResource(ArtworkSellingService artworkSellingService, ArtworkSellingRepository artworkSellingRepository) {
        this.artworkSellingService = artworkSellingService;
    }

    /**
     * {@code POST  /sellings} : Create a new artworkSelling.
     *
     * @param artworkSellingDTO the artworkSellingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new artworkSellingDTO, or with status
     *         {@code 400 (Bad Request)} if the artworkSelling has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("{artworkId}/sellings")
    public ResponseEntity<ArtworkSellingDTO> createArtworkSelling(
        @PathVariable(name = "artworkId") Long artworkId,
        @Valid @RequestBody ArtworkSellingDTO artworkSellingDTO
    ) {
        log.debug("REST request to save ArtworkSelling : {}", artworkSellingDTO);

        ArtworkSellingDTO result = artworkSellingService.save(artworkId, artworkSellingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("{artworkId}/sellings/{id}/direct-buy")
    public ResponseEntity<ArtworkSellingDTO> directBuyArtwork(
        @PathVariable(name = "artworkId") Long artworkId,
        @PathVariable(name = "id") Long id
    ) {
        log.debug(" direct buy request : {}, {}", artworkId, id);

        ArtworkSellingDTO result = artworkSellingService.buyDirect(id, artworkId);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /artwork-sellings} : get all the artworkSellings.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of artworkSellings in body.
     */
    @GetMapping("/sellings")
    public List<ArtworkSellingDTO> getAllArtworkSellings(@RequestParam(name = "filter", required = false) String filter) {
        if ("artwork-is-null".equals(filter)) {
            log.debug("REST request to get all ArtworkSellings where artwork is null");
            return artworkSellingService.findAllWhereArtworkIsNull();
        }
        log.debug("REST request to get all ArtworkSellings");
        return artworkSellingService.findAll();
    }

    /**
     * {@code GET  /artwork-sellings/:id} : get the "id" artworkSelling.
     *
     * @param id the id of the artworkSellingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the artworkSellingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{artworkId}/sellings/{id}")
    public ResponseEntity<ArtworkSellingDTO> getArtworkSelling(@PathVariable("artworkId") Long artworkId, @PathVariable("id") Long id) {
        log.debug("REST request to get ArtworkSelling : {}", id);
        Optional<ArtworkSellingDTO> artworkSellingDTO = artworkSellingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkSellingDTO);
    }

    /**
     * {@code DELETE  /artwork-sellings/:id} : delete the "id" artworkSelling.
     *
     * @param id the id of the artworkSellingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{artworkId}/sellings/{id}")
    public ResponseEntity<Void> deleteArtworkSelling(@PathVariable("artworkId") Long artworkId, @PathVariable("id") Long id) {
        log.debug("REST request to delete ArtworkSelling : {}", id);
        artworkSellingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    // /**
    // * {@code PUT /artwork-sellings/:id} : Updates an existing artworkSelling.
    // *
    // * @param id the id of the artworkSellingDTO to save.
    // * @param artworkSellingDTO the artworkSellingDTO to update.
    // * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
    // body
    // * the updated artworkSellingDTO,
    // * or with status {@code 400 (Bad Request)} if the artworkSellingDTO is
    // * not valid,
    // * or with status {@code 500 (Internal Server Error)} if the
    // * artworkSellingDTO couldn't be updated.
    // * @throws URISyntaxException if the Location URI syntax is incorrect.
    // */
    // @PutMapping("/{id}")
    // public ResponseEntity<ArtworkSellingDTO> updateArtworkSelling(
    // @PathVariable(value = "id", required = false) final Long id,
    // @RequestBody ArtworkSellingDTO artworkSellingDTO) throws URISyntaxException {
    // log.debug("REST request to update ArtworkSelling : {}, {}", id,
    // artworkSellingDTO);
    // if (artworkSellingDTO.getId() == null) {
    // throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    // }
    // if (!Objects.equals(id, artworkSellingDTO.getId())) {
    // throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    // }

    // if (!artworkSellingRepository.existsById(id)) {
    // throw new BadRequestAlertException("Entity not found", ENTITY_NAME,
    // "idnotfound");
    // }

    // ArtworkSellingDTO result = artworkSellingService.update(artworkSellingDTO);
    // return ResponseEntity
    // .ok()
    // .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false,
    // ENTITY_NAME,
    // artworkSellingDTO.getId().toString()))
    // .body(result);
    // }

}
