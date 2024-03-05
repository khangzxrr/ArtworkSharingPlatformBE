package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.ArtworkCommentRepository;
import com.github.khangzxrr.repository.ArtworkRepository;
import com.github.khangzxrr.service.ArtworkCommentService;
import com.github.khangzxrr.service.ArtworkService;
import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import com.github.khangzxrr.service.dto.ArtworkDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/PostOfArtWork")
public class ArtworkPostResourse {

    private final Logger log = LoggerFactory.getLogger(ArtworkResource.class);

    private static final String ENTITY_NAME = "artworkComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkService artworkService;

    private final ArtworkRepository artworkRepository;

    private final ArtworkCommentService artworkCommentService;

    private final ArtworkCommentRepository artworkCommentRepository;

    public ArtworkPostResourse(
        ArtworkService artworkService,
        ArtworkRepository artworkRepository,
        ArtworkCommentService artworkCommentService,
        ArtworkCommentRepository artworkCommentRepository
    ) {
        this.artworkService = artworkService;
        this.artworkRepository = artworkRepository;
        this.artworkCommentService = artworkCommentService;
        this.artworkCommentRepository = artworkCommentRepository;
    }

    @GetMapping("/getAll/likeAndcmt/{id}")
    public ResponseEntity<ArtworkDTO> getArtwork(@PathVariable("id") Long id) {
        log.debug("REST request to get Artwork : {}", id);
        Optional<ArtworkDTO> artworkDTO = artworkService.findAllPostLike(id);
        return ResponseUtil.wrapOrNotFound(artworkDTO);
    }

    @PostMapping("/user/commentArtwork")
    public ResponseEntity<ArtworkCommentDTO> createArtworkComment(@RequestBody ArtworkCommentDTO artworkCommentDTO)
        throws URISyntaxException {
        log.debug("REST request to save ArtworkComment : {}", artworkCommentDTO);
        if (artworkCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new artworkComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtworkCommentDTO result = artworkCommentService.save(artworkCommentDTO);
        return ResponseEntity
            .created(new URI("/api/artwork-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtworkCommentDTO> getArtworkComment(@PathVariable("id") Long id) {
        log.debug("REST request to get ArtworkComment : {}", id);
        Optional<ArtworkCommentDTO> artworkCommentDTO = artworkCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkCommentDTO);
    }

    @PutMapping("/user/editThierPost/{id}")
    public ResponseEntity<ArtworkCommentDTO> updateArtworkComment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ArtworkCommentDTO artworkCommentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtworkComment : {}, {}", id, artworkCommentDTO);
        if (artworkCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkCommentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtworkCommentDTO result = artworkCommentService.update(artworkCommentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, artworkCommentDTO.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/user/delete/cmt/{id}")
    public ResponseEntity<Void> deleteArtworkComment(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArtworkComment : {}", id);
        artworkCommentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
