package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.ArtworkComplainDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.ArtworkComplain}.
 */
public interface ArtworkComplainService {
    /**
     * Save a artworkComplain.
     *
     * @param artworkComplainDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkComplainDTO save(ArtworkComplainDTO artworkComplainDTO);

    /**
     * Updates a artworkComplain.
     *
     * @param artworkComplainDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkComplainDTO update(ArtworkComplainDTO artworkComplainDTO);

    /**
     * Partially updates a artworkComplain.
     *
     * @param artworkComplainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkComplainDTO> partialUpdate(ArtworkComplainDTO artworkComplainDTO);

    /**
     * Get all the artworkComplains.
     *
     * @return the list of entities.
     */
    List<ArtworkComplainDTO> findAll();

    /**
     * Get the "id" artworkComplain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkComplainDTO> findOne(Long id);

    /**
     * Delete the "id" artworkComplain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
