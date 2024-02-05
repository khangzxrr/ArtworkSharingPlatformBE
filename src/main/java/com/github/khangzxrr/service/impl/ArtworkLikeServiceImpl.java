package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.ArtworkLike;
import com.github.khangzxrr.repository.ArtworkLikeRepository;
import com.github.khangzxrr.service.ArtworkLikeService;
import com.github.khangzxrr.service.dto.ArtworkLikeDTO;
import com.github.khangzxrr.service.mapper.ArtworkLikeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.ArtworkLike}.
 */
@Service
@Transactional
public class ArtworkLikeServiceImpl implements ArtworkLikeService {

    private final Logger log = LoggerFactory.getLogger(ArtworkLikeServiceImpl.class);

    private final ArtworkLikeRepository artworkLikeRepository;

    private final ArtworkLikeMapper artworkLikeMapper;

    public ArtworkLikeServiceImpl(ArtworkLikeRepository artworkLikeRepository, ArtworkLikeMapper artworkLikeMapper) {
        this.artworkLikeRepository = artworkLikeRepository;
        this.artworkLikeMapper = artworkLikeMapper;
    }

    @Override
    public ArtworkLikeDTO save(ArtworkLikeDTO artworkLikeDTO) {
        log.debug("Request to save ArtworkLike : {}", artworkLikeDTO);
        ArtworkLike artworkLike = artworkLikeMapper.toEntity(artworkLikeDTO);
        artworkLike = artworkLikeRepository.save(artworkLike);
        return artworkLikeMapper.toDto(artworkLike);
    }

    @Override
    public ArtworkLikeDTO update(ArtworkLikeDTO artworkLikeDTO) {
        log.debug("Request to update ArtworkLike : {}", artworkLikeDTO);
        ArtworkLike artworkLike = artworkLikeMapper.toEntity(artworkLikeDTO);
        artworkLike = artworkLikeRepository.save(artworkLike);
        return artworkLikeMapper.toDto(artworkLike);
    }

    @Override
    public Optional<ArtworkLikeDTO> partialUpdate(ArtworkLikeDTO artworkLikeDTO) {
        log.debug("Request to partially update ArtworkLike : {}", artworkLikeDTO);

        return artworkLikeRepository
            .findById(artworkLikeDTO.getId())
            .map(existingArtworkLike -> {
                artworkLikeMapper.partialUpdate(existingArtworkLike, artworkLikeDTO);

                return existingArtworkLike;
            })
            .map(artworkLikeRepository::save)
            .map(artworkLikeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkLikeDTO> findAll() {
        log.debug("Request to get all ArtworkLikes");
        return artworkLikeRepository.findAll().stream().map(artworkLikeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkLikeDTO> findOne(Long id) {
        log.debug("Request to get ArtworkLike : {}", id);
        return artworkLikeRepository.findById(id).map(artworkLikeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkLike : {}", id);
        artworkLikeRepository.deleteById(id);
    }
}
