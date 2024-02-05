package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.ArtworkLikeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.ArtworkLike}.
 */
public interface ArtworkLikeService {
    /**
     * Save a artworkLike.
     *
     * @param artworkLikeDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkLikeDTO save(ArtworkLikeDTO artworkLikeDTO);

    /**
     * Updates a artworkLike.
     *
     * @param artworkLikeDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkLikeDTO update(ArtworkLikeDTO artworkLikeDTO);

    /**
     * Partially updates a artworkLike.
     *
     * @param artworkLikeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkLikeDTO> partialUpdate(ArtworkLikeDTO artworkLikeDTO);

    /**
     * Get all the artworkLikes.
     *
     * @return the list of entities.
     */
    List<ArtworkLikeDTO> findAll();

    /**
     * Get the "id" artworkLike.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkLikeDTO> findOne(Long id);

    /**
     * Delete the "id" artworkLike.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
