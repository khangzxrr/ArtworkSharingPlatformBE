package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.RequestBidDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.RequestBid}.
 */
public interface RequestBidService {
    /**
     * Save a requestBid.
     *
     * @param requestBidDTO the entity to save.
     * @return the persisted entity.
     */
    RequestBidDTO save(RequestBidDTO requestBidDTO);

    /**
     * Updates a requestBid.
     *
     * @param requestBidDTO the entity to update.
     * @return the persisted entity.
     */
    RequestBidDTO update(RequestBidDTO requestBidDTO);

    /**
     * Partially updates a requestBid.
     *
     * @param requestBidDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestBidDTO> partialUpdate(RequestBidDTO requestBidDTO);

    /**
     * Get all the requestBids.
     *
     * @return the list of entities.
     */
    List<RequestBidDTO> findAll();

    /**
     * Get the "id" requestBid.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestBidDTO> findOne(Long id);

    /**
     * Delete the "id" requestBid.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
