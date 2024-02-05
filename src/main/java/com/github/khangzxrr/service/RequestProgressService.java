package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.RequestProgressDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.RequestProgress}.
 */
public interface RequestProgressService {
    /**
     * Save a requestProgress.
     *
     * @param requestProgressDTO the entity to save.
     * @return the persisted entity.
     */
    RequestProgressDTO save(RequestProgressDTO requestProgressDTO);

    /**
     * Updates a requestProgress.
     *
     * @param requestProgressDTO the entity to update.
     * @return the persisted entity.
     */
    RequestProgressDTO update(RequestProgressDTO requestProgressDTO);

    /**
     * Partially updates a requestProgress.
     *
     * @param requestProgressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestProgressDTO> partialUpdate(RequestProgressDTO requestProgressDTO);

    /**
     * Get all the requestProgresses.
     *
     * @return the list of entities.
     */
    List<RequestProgressDTO> findAll();

    /**
     * Get the "id" requestProgress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestProgressDTO> findOne(Long id);

    /**
     * Delete the "id" requestProgress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
