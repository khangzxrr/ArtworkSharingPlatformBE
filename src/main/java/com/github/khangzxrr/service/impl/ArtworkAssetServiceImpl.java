package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.ArtworkAsset;
import com.github.khangzxrr.repository.ArtworkAssetRepository;
import com.github.khangzxrr.service.ArtworkAssetService;
import com.github.khangzxrr.service.dto.ArtworkAssetDTO;
import com.github.khangzxrr.service.mapper.ArtworkAssetMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.ArtworkAsset}.
 */
@Service
@Transactional
public class ArtworkAssetServiceImpl implements ArtworkAssetService {

    private final Logger log = LoggerFactory.getLogger(ArtworkAssetServiceImpl.class);

    private final ArtworkAssetRepository artworkAssetRepository;

    private final ArtworkAssetMapper artworkAssetMapper;

    public ArtworkAssetServiceImpl(ArtworkAssetRepository artworkAssetRepository, ArtworkAssetMapper artworkAssetMapper) {
        this.artworkAssetRepository = artworkAssetRepository;
        this.artworkAssetMapper = artworkAssetMapper;
    }

    @Override
    public ArtworkAssetDTO save(ArtworkAssetDTO artworkAssetDTO) {
        log.debug("Request to save ArtworkAsset : {}", artworkAssetDTO);
        ArtworkAsset artworkAsset = artworkAssetMapper.toEntity(artworkAssetDTO);
        artworkAsset = artworkAssetRepository.save(artworkAsset);
        return artworkAssetMapper.toDto(artworkAsset);
    }

    @Override
    public ArtworkAssetDTO update(ArtworkAssetDTO artworkAssetDTO) {
        log.debug("Request to update ArtworkAsset : {}", artworkAssetDTO);
        ArtworkAsset artworkAsset = artworkAssetMapper.toEntity(artworkAssetDTO);
        artworkAsset = artworkAssetRepository.save(artworkAsset);
        return artworkAssetMapper.toDto(artworkAsset);
    }

    @Override
    public Optional<ArtworkAssetDTO> partialUpdate(ArtworkAssetDTO artworkAssetDTO) {
        log.debug("Request to partially update ArtworkAsset : {}", artworkAssetDTO);

        return artworkAssetRepository
            .findById(artworkAssetDTO.getId())
            .map(existingArtworkAsset -> {
                artworkAssetMapper.partialUpdate(existingArtworkAsset, artworkAssetDTO);

                return existingArtworkAsset;
            })
            .map(artworkAssetRepository::save)
            .map(artworkAssetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkAssetDTO> findAll() {
        log.debug("Request to get all ArtworkAssets");
        return artworkAssetRepository.findAll().stream().map(artworkAssetMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkAssetDTO> findOne(Long id) {
        log.debug("Request to get ArtworkAsset : {}", id);
        return artworkAssetRepository.findById(id).map(artworkAssetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkAsset : {}", id);
        artworkAssetRepository.deleteById(id);
    }
}
