package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.ArtworkAssetDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.ArtworkAsset}.
 */
public interface ArtworkAssetService {
    /**
     * Save a artworkAsset.
     *
     * @param artworkAssetDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkAssetDTO save(ArtworkAssetDTO artworkAssetDTO);

    /**
     * Updates a artworkAsset.
     *
     * @param artworkAssetDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkAssetDTO update(ArtworkAssetDTO artworkAssetDTO);

    /**
     * Partially updates a artworkAsset.
     *
     * @param artworkAssetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkAssetDTO> partialUpdate(ArtworkAssetDTO artworkAssetDTO);

    /**
     * Get all the artworkAssets.
     *
     * @return the list of entities.
     */
    List<ArtworkAssetDTO> findAll();

    /**
     * Get the "id" artworkAsset.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkAssetDTO> findOne(Long id);

    /**
     * Delete the "id" artworkAsset.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
