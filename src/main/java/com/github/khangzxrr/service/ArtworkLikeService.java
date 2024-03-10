package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.ArtworkLikeDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.ArtworkLike}.
 */
public interface ArtworkLikeService {
    void Like(Long id);
    void Unlike(Long id);
    Optional<ArtworkLikeDTO> getLikeByUser(Long artworkId);
}
