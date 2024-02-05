package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.ArtworkCategory}.
 */
public interface ArtworkCategoryService {
    /**
     * Save a artworkCategory.
     *
     * @param artworkCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkCategoryDTO save(ArtworkCategoryDTO artworkCategoryDTO);

    /**
     * Updates a artworkCategory.
     *
     * @param artworkCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkCategoryDTO update(ArtworkCategoryDTO artworkCategoryDTO);

    /**
     * Partially updates a artworkCategory.
     *
     * @param artworkCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkCategoryDTO> partialUpdate(ArtworkCategoryDTO artworkCategoryDTO);

    /**
     * Get all the artworkCategories.
     *
     * @return the list of entities.
     */
    List<ArtworkCategoryDTO> findAll();

    /**
     * Get the "id" artworkCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" artworkCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
