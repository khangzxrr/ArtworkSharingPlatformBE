package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.RequestAttachmentDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.RequestAttachment}.
 */
public interface RequestAttachmentService {
    /**
     * Save a requestAttachment.
     *
     * @param requestAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    RequestAttachmentDTO save(RequestAttachmentDTO requestAttachmentDTO);

    /**
     * Updates a requestAttachment.
     *
     * @param requestAttachmentDTO the entity to update.
     * @return the persisted entity.
     */
    RequestAttachmentDTO update(RequestAttachmentDTO requestAttachmentDTO);

    /**
     * Partially updates a requestAttachment.
     *
     * @param requestAttachmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RequestAttachmentDTO> partialUpdate(RequestAttachmentDTO requestAttachmentDTO);

    /**
     * Get all the requestAttachments.
     *
     * @return the list of entities.
     */
    List<RequestAttachmentDTO> findAll();

    /**
     * Get the "id" requestAttachment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestAttachmentDTO> findOne(Long id);

    /**
     * Delete the "id" requestAttachment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
