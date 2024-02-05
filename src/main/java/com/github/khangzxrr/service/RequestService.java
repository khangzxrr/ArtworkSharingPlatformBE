package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.RequestDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.Request}.
 */
public interface RequestService {
    /**
     * Save a request.
     *
     * @param requestDTO the entity to save.
     * @return the persisted entity.
     */
    RequestDTO save(RequestDTO requestDTO);

    /**
     * Updates a request.
     *
     * @param requestDTO the entity to update.
     * @return the persisted entity.
     */
    RequestDTO update(RequestDTO requestDTO);

    /**
     * Partially updates a request.
     *
     * @param requestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestDTO> partialUpdate(RequestDTO requestDTO);

    /**
     * Get all the requests.
     *
     * @return the list of entities.
     */
    List<RequestDTO> findAll();

    /**
     * Get the "id" request.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestDTO> findOne(Long id);

    /**
     * Delete the "id" request.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
