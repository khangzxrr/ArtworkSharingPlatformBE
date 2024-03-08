package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.ArtworkCommentService;
import com.github.khangzxrr.service.CreateArtworkCommentDTO;
import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
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

/**
 * REST controller for managing
 * {@link com.github.khangzxrr.domain.ArtworkComment}.
 */
@RestController
@RequestMapping({ "/api/audience/artworks", "/api/creator/artworks" })
public class ArtworkCommentResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkCommentResource.class);

    private static final String ENTITY_NAME = "artworkComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkCommentService artworkCommentService;

    public ArtworkCommentResource(ArtworkCommentService artworkCommentService) {
        this.artworkCommentService = artworkCommentService;
    }

    /**
     * {@code POST  /artwork-comments} : Create a new artworkComment.
     *
     * @param artworkCommentDTO the artworkCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new artworkCommentDTO, or with status
     *         {@code 400 (Bad Request)} if the artworkComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("{artworkId}/comments")
    public ResponseEntity<ArtworkCommentDTO> createArtworkComment(
        @PathVariable("artworkId") Long artworkId,
        @Valid @RequestBody CreateArtworkCommentDTO createArtworkCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ArtworkComment : {}", createArtworkCommentDTO);

        ArtworkCommentDTO result = artworkCommentService.comment(artworkId, createArtworkCommentDTO);

        return ResponseEntity
            .created(new URI("/api/artwork-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /artwork-comments} : get all the artworkComments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of artworkComments in body.
     */
    @GetMapping("{artworkId}/comments")
    public ResponseEntity<List<ArtworkCommentDTO>> getAllArtworkComments(
        @PathVariable("artworkId") Long artworkId,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all ArtworkComments");

        Page<ArtworkCommentDTO> page = artworkCommentService.getAll(artworkId, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @DeleteMapping("{artworkId}/comments/{commentId}")
    public ResponseEntity<Void> deleteArtworkComment(@PathVariable("artworkId") Long artworkId, @PathVariable("commentId") Long commentId) {
        log.debug("REST request to delete ArtworkComment : {}", commentId);

        artworkCommentService.delete(artworkId, commentId);

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, commentId.toString()))
            .build();
    }
}
