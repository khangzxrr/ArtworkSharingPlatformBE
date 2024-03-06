package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.CreateArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.UpdateArtworkDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    ArtworkDTO save(CreateArtworkDTO createArtworkDTO);

    /**
     * Updates a artwork.
     *
     * @param artworkDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkDTO update(Long id, UpdateArtworkDTO updateArtworkDTO);

    Page<ArtworkDTO> findAllArtworksOfUser(Pageable pageable);

    Page<ArtworkDTO> findAllPublicArtworks(Pageable pageable);

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

    void Like(Long id);

    void Unlike(Long id);

    ArtworkCommentDTO Commend(Long id, CreateArtworkCommentDTO createArtworkCommentDTO);
}
