package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.ArtworkRepository;
import com.github.khangzxrr.service.ArtworkService;
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

    private static final String ENTITY_NAME = "artwork";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkService artworkService;

    private final ArtworkRepository artworkRepository;

    public ArtworkPostResourse(ArtworkService artworkService, ArtworkRepository artworkRepository) {
        this.artworkService = artworkService;
        this.artworkRepository = artworkRepository;
    }

    @GetMapping("/getAll/likeAndcmt/{id}")
    public ResponseEntity<ArtworkDTO> getArtwork(@PathVariable("id") Long id) {
        log.debug("REST request to get Artwork : {}", id);
        Optional<ArtworkDTO> artworkDTO = artworkService.findAllPostLike(id);
        return ResponseUtil.wrapOrNotFound(artworkDTO);
    }
}
