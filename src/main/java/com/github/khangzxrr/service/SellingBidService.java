package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.SellingBidDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.SellingBid}.
 */
public interface SellingBidService {
    /**
     * Save a sellingBid.
     *
     * @param sellingBidDTO the entity to save.
     * @return the persisted entity.
     */
    SellingBidDTO save(SellingBidDTO sellingBidDTO);

    /**
     * Updates a sellingBid.
     *
     * @param sellingBidDTO the entity to update.
     * @return the persisted entity.
     */
    SellingBidDTO update(SellingBidDTO sellingBidDTO);

    /**
     * Partially updates a sellingBid.
     *
     * @param sellingBidDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SellingBidDTO> partialUpdate(SellingBidDTO sellingBidDTO);

    /**
     * Get all the sellingBids.
     *
     * @return the list of entities.
     */
    List<SellingBidDTO> findAll();

    /**
     * Get the "id" sellingBid.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SellingBidDTO> findOne(Long id);

    /**
     * Delete the "id" sellingBid.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
