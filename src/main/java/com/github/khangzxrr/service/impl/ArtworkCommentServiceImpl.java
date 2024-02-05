package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.ArtworkComment;
import com.github.khangzxrr.repository.ArtworkCommentRepository;
import com.github.khangzxrr.service.ArtworkCommentService;
import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import com.github.khangzxrr.service.mapper.ArtworkCommentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.ArtworkComment}.
 */
@Service
@Transactional
public class ArtworkCommentServiceImpl implements ArtworkCommentService {

    private final Logger log = LoggerFactory.getLogger(ArtworkCommentServiceImpl.class);

    private final ArtworkCommentRepository artworkCommentRepository;

    private final ArtworkCommentMapper artworkCommentMapper;

    public ArtworkCommentServiceImpl(ArtworkCommentRepository artworkCommentRepository, ArtworkCommentMapper artworkCommentMapper) {
        this.artworkCommentRepository = artworkCommentRepository;
        this.artworkCommentMapper = artworkCommentMapper;
    }

    @Override
    public ArtworkCommentDTO save(ArtworkCommentDTO artworkCommentDTO) {
        log.debug("Request to save ArtworkComment : {}", artworkCommentDTO);
        ArtworkComment artworkComment = artworkCommentMapper.toEntity(artworkCommentDTO);
        artworkComment = artworkCommentRepository.save(artworkComment);
        return artworkCommentMapper.toDto(artworkComment);
    }

    @Override
    public ArtworkCommentDTO update(ArtworkCommentDTO artworkCommentDTO) {
        log.debug("Request to update ArtworkComment : {}", artworkCommentDTO);
        ArtworkComment artworkComment = artworkCommentMapper.toEntity(artworkCommentDTO);
        artworkComment = artworkCommentRepository.save(artworkComment);
        return artworkCommentMapper.toDto(artworkComment);
    }

    @Override
    public Optional<ArtworkCommentDTO> partialUpdate(ArtworkCommentDTO artworkCommentDTO) {
        log.debug("Request to partially update ArtworkComment : {}", artworkCommentDTO);

        return artworkCommentRepository
            .findById(artworkCommentDTO.getId())
            .map(existingArtworkComment -> {
                artworkCommentMapper.partialUpdate(existingArtworkComment, artworkCommentDTO);

                return existingArtworkComment;
            })
            .map(artworkCommentRepository::save)
            .map(artworkCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkCommentDTO> findAll() {
        log.debug("Request to get all ArtworkComments");
        return artworkCommentRepository
            .findAll()
            .stream()
            .map(artworkCommentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkCommentDTO> findOne(Long id) {
        log.debug("Request to get ArtworkComment : {}", id);
        return artworkCommentRepository.findById(id).map(artworkCommentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkComment : {}", id);
        artworkCommentRepository.deleteById(id);
    }
}
