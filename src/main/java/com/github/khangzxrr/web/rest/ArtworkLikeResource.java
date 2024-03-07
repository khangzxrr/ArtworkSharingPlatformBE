package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.service.ArtworkLikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.ArtworkLike}.
 */
@RestController
@RequestMapping({ "/api/creator/artworks", "/api/audience/artworks" })
public class ArtworkLikeResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkLikeResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkLikeService artworkLikeService;

    public ArtworkLikeResource(ArtworkLikeService artworkLikeService) {
        this.artworkLikeService = artworkLikeService;
    }

    @PostMapping("{artworkId}/like")
    public ResponseEntity<Void> likeArtwork(@PathVariable("artworkId") Long artworkId) {
        log.debug("REST request like artwork");

        artworkLikeService.Like(artworkId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("{artworkId}/unlike")
    public ResponseEntity<Void> unlikeArtwork(@PathVariable("artworkId") Long artworkId) {
        log.debug("REST request like artwork");

        artworkLikeService.Unlike(artworkId);

        return ResponseEntity.ok().build();
    }
}
