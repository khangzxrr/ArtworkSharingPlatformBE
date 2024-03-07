package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.ArtworkComment}.
 */
public interface ArtworkCommentService {
    Page<ArtworkCommentDTO> getAll(Long artworkId, Pageable pageable);

    ArtworkCommentDTO comment(Long id, CreateArtworkCommentDTO createArtworkCommentDTO);

    void delete(Long artworkId, Long commentId);
}
