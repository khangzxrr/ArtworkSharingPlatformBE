package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.ArtworkDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.Artwork}.
 */
public interface ArtworkService {
    /**
     * Save a artwork.
     *
     * @param artworkDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkDTO save(ArtworkDTO artworkDTO);

    /**
     * Updates a artwork.
     *
     * @param artworkDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkDTO update(ArtworkDTO artworkDTO);

    ArtworkDTO updateSaleDirect(ArtworkDTO artworkDTO);
    /**
     * Partially updates a artwork.
     *
     * @param artworkDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkDTO> partialUpdate(ArtworkDTO artworkDTO);

    /**
     * Get all the artworks.
     *
     * @return the list of entities.
     */
    List<ArtworkDTO> findAll();

    /**
     * Get the "id" artwork.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkDTO> findOne(Long id);

    /**
     * Delete the "id" artwork.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void cancel(Long id);

    int purchaseArtwork(Long artworkId);
}
