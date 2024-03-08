package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.ArtworkService;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.UpdateArtworkDTO;
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
 * REST controller for managing {@link com.github.khangzxrr.domain.Artwork}.
 */
@RestController
@RequestMapping({ "/api/audience/artworks", "/api/creator/artworks" })
public class ArtworkResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkResource.class);

    private static final String ENTITY_NAME = "artwork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkService artworkService;

    public ArtworkResource(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    /**
     * {@code PUT  /artworks/:id} : Updates an existing artwork.
     *
     * @param artworkId         the id of the artworkDTO to save.
     * @param artworkDTO the artworkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated artworkDTO,
     *         or with status {@code 400 (Bad Request)} if the artworkDTO is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the artworkDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{artworkId}")
    public ResponseEntity<ArtworkDTO> updateArtwork(
        @PathVariable(value = "artworkId") final Long artworkId,
        @RequestBody UpdateArtworkDTO updateArtworkDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Artwork : {}, {}", artworkId, updateArtworkDTO);

        ArtworkDTO result = artworkService.update(artworkId, updateArtworkDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /artworks} : get all the artworks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of artworks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArtworkDTO>> getAllPublicArtworks(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all Artworks");

        Page<ArtworkDTO> artworkPages = artworkService.findAllPublicArtworks(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), artworkPages);
        return ResponseEntity.ok().headers(headers).body(artworkPages.getContent());
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ArtworkDTO>> getAllPrivateArtworks(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all Artworks of current user");

        Page<ArtworkDTO> artworkPages = artworkService.findAllArtworksOfUser(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), artworkPages);
        return ResponseEntity.ok().headers(headers).body(artworkPages.getContent());
    }

    /**
     * {@code GET  /artworks/:id} : get the "id" artwork.
     *
     * @param id the id of the artworkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the artworkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtworkDTO> getArtwork(@PathVariable("id") Long id) {
        log.debug("REST request to get Artwork : {}", id);
        Optional<ArtworkDTO> artworkDTO = artworkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkDTO);
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
