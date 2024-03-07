package com.github.khangzxrr.service;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.ArtworkLike}.
 */
public interface ArtworkLikeService {
    void Like(Long id);
    void Unlike(Long id);
}
