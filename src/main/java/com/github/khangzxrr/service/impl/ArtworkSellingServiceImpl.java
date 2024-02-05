package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.repository.ArtworkSellingRepository;
import com.github.khangzxrr.service.ArtworkSellingService;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import com.github.khangzxrr.service.mapper.ArtworkSellingMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.ArtworkSelling}.
 */
@Service
@Transactional
public class ArtworkSellingServiceImpl implements ArtworkSellingService {

    private final Logger log = LoggerFactory.getLogger(ArtworkSellingServiceImpl.class);

    private final ArtworkSellingRepository artworkSellingRepository;

    private final ArtworkSellingMapper artworkSellingMapper;

    public ArtworkSellingServiceImpl(ArtworkSellingRepository artworkSellingRepository, ArtworkSellingMapper artworkSellingMapper) {
        this.artworkSellingRepository = artworkSellingRepository;
        this.artworkSellingMapper = artworkSellingMapper;
    }

    @Override
    public ArtworkSellingDTO save(ArtworkSellingDTO artworkSellingDTO) {
        log.debug("Request to save ArtworkSelling : {}", artworkSellingDTO);
        ArtworkSelling artworkSelling = artworkSellingMapper.toEntity(artworkSellingDTO);
        artworkSelling = artworkSellingRepository.save(artworkSelling);
        return artworkSellingMapper.toDto(artworkSelling);
    }

    @Override
    public ArtworkSellingDTO update(ArtworkSellingDTO artworkSellingDTO) {
        log.debug("Request to update ArtworkSelling : {}", artworkSellingDTO);
        ArtworkSelling artworkSelling = artworkSellingMapper.toEntity(artworkSellingDTO);
        artworkSelling = artworkSellingRepository.save(artworkSelling);
        return artworkSellingMapper.toDto(artworkSelling);
    }

    @Override
    public Optional<ArtworkSellingDTO> partialUpdate(ArtworkSellingDTO artworkSellingDTO) {
        log.debug("Request to partially update ArtworkSelling : {}", artworkSellingDTO);

        return artworkSellingRepository
            .findById(artworkSellingDTO.getId())
            .map(existingArtworkSelling -> {
                artworkSellingMapper.partialUpdate(existingArtworkSelling, artworkSellingDTO);

                return existingArtworkSelling;
            })
            .map(artworkSellingRepository::save)
            .map(artworkSellingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkSellingDTO> findAll() {
        log.debug("Request to get all ArtworkSellings");
        return artworkSellingRepository
            .findAll()
            .stream()
            .map(artworkSellingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the artworkSellings where Artwork is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ArtworkSellingDTO> findAllWhereArtworkIsNull() {
        log.debug("Request to get all artworkSellings where Artwork is null");
        return StreamSupport
            .stream(artworkSellingRepository.findAll().spliterator(), false)
            .filter(artworkSelling -> artworkSelling.getArtwork() == null)
            .map(artworkSellingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkSellingDTO> findOne(Long id) {
        log.debug("Request to get ArtworkSelling : {}", id);
        return artworkSellingRepository.findById(id).map(artworkSellingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkSelling : {}", id);
        artworkSellingRepository.deleteById(id);
    }
}
