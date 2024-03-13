package com.github.khangzxrr.service;

import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.ArtworkSelling}.
 */
public interface ArtworkSellingService {
    ArtworkSellingDTO buyDirect(Long id, Long artworkId);

    Optional<ArtworkSelling> getOnGoingSellingByArtworkId(Long artworkId);

    /**
     * Save a artworkSelling.
     *
     * @param artworkSellingDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkSellingDTO save(Long artworkId, ArtworkSellingDTO artworkSellingDTO);

    /**
     * Updates a artworkSelling.
     *
     * @param artworkSellingDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkSellingDTO update(ArtworkSellingDTO artworkSellingDTO);

    /**
     * Partially updates a artworkSelling.
     *
     * @param artworkSellingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkSellingDTO> partialUpdate(ArtworkSellingDTO artworkSellingDTO);

    /**
     * Get all the artworkSellings.
     *
     * @return the list of entities.
     */
    List<ArtworkSellingDTO> findAll();

    /**
     * Get all the ArtworkSellingDTO where Artwork is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ArtworkSellingDTO> findAllWhereArtworkIsNull();

    /**
     * Get the "id" artworkSelling.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkSellingDTO> findOne(Long id);

    /**
     * Delete the "id" artworkSelling.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
