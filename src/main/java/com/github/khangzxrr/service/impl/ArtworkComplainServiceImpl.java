package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.ArtworkComplain;
import com.github.khangzxrr.repository.ArtworkComplainRepository;
import com.github.khangzxrr.service.ArtworkComplainService;
import com.github.khangzxrr.service.dto.ArtworkComplainDTO;
import com.github.khangzxrr.service.mapper.ArtworkComplainMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.ArtworkComplain}.
 */
@Service
@Transactional
public class ArtworkComplainServiceImpl implements ArtworkComplainService {

    private final Logger log = LoggerFactory.getLogger(ArtworkComplainServiceImpl.class);

    private final ArtworkComplainRepository artworkComplainRepository;

    private final ArtworkComplainMapper artworkComplainMapper;

    public ArtworkComplainServiceImpl(ArtworkComplainRepository artworkComplainRepository, ArtworkComplainMapper artworkComplainMapper) {
        this.artworkComplainRepository = artworkComplainRepository;
        this.artworkComplainMapper = artworkComplainMapper;
    }

    @Override
    public ArtworkComplainDTO save(ArtworkComplainDTO artworkComplainDTO) {
        log.debug("Request to save ArtworkComplain : {}", artworkComplainDTO);
        ArtworkComplain artworkComplain = artworkComplainMapper.toEntity(artworkComplainDTO);
        artworkComplain = artworkComplainRepository.save(artworkComplain);
        return artworkComplainMapper.toDto(artworkComplain);
    }

    @Override
    public ArtworkComplainDTO update(ArtworkComplainDTO artworkComplainDTO) {
        log.debug("Request to update ArtworkComplain : {}", artworkComplainDTO);
        ArtworkComplain artworkComplain = artworkComplainMapper.toEntity(artworkComplainDTO);
        artworkComplain = artworkComplainRepository.save(artworkComplain);
        return artworkComplainMapper.toDto(artworkComplain);
    }

    @Override
    public Optional<ArtworkComplainDTO> partialUpdate(ArtworkComplainDTO artworkComplainDTO) {
        log.debug("Request to partially update ArtworkComplain : {}", artworkComplainDTO);

        return artworkComplainRepository
            .findById(artworkComplainDTO.getId())
            .map(existingArtworkComplain -> {
                artworkComplainMapper.partialUpdate(existingArtworkComplain, artworkComplainDTO);

                return existingArtworkComplain;
            })
            .map(artworkComplainRepository::save)
            .map(artworkComplainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkComplainDTO> findAll() {
        log.debug("Request to get all ArtworkComplains");
        return artworkComplainRepository
            .findAll()
            .stream()
            .map(artworkComplainMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkComplainDTO> findOne(Long id) {
        log.debug("Request to get ArtworkComplain : {}", id);
        return artworkComplainRepository.findById(id).map(artworkComplainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkComplain : {}", id);
        artworkComplainRepository.deleteById(id);
    }
}
