package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.ArtworkComment}.
 */
public interface ArtworkCommentService {
    /**
     * Save a artworkComment.
     *
     * @param artworkCommentDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkCommentDTO save(ArtworkCommentDTO artworkCommentDTO);

    /**
     * Updates a artworkComment.
     *
     * @param artworkCommentDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkCommentDTO update(ArtworkCommentDTO artworkCommentDTO);

    /**
     * Partially updates a artworkComment.
     *
     * @param artworkCommentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkCommentDTO> partialUpdate(ArtworkCommentDTO artworkCommentDTO);

    /**
     * Get all the artworkComments.
     *
     * @return the list of entities.
     */
    List<ArtworkCommentDTO> findAll();

    /**
     * Get the "id" artworkComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkCommentDTO> findOne(Long id);

    /**
     * Delete the "id" artworkComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
