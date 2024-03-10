package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.ArtworkCategory;
import com.github.khangzxrr.repository.ArtworkCategoryRepository;
import com.github.khangzxrr.service.ArtworkCategoryService;
import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
import com.github.khangzxrr.service.mapper.ArtworkCategoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.ArtworkCategory}.
 */
@Service
@Transactional
public class ArtworkCategoryServiceImpl implements ArtworkCategoryService {

    private final Logger log = LoggerFactory.getLogger(ArtworkCategoryServiceImpl.class);

    private final ArtworkCategoryRepository artworkCategoryRepository;

    private final ArtworkCategoryMapper artworkCategoryMapper;

    public ArtworkCategoryServiceImpl(ArtworkCategoryRepository artworkCategoryRepository, ArtworkCategoryMapper artworkCategoryMapper) {
        this.artworkCategoryRepository = artworkCategoryRepository;
        this.artworkCategoryMapper = artworkCategoryMapper;
    }

    @Override
    public ArtworkCategoryDTO save(ArtworkCategoryDTO artworkCategoryDTO) {
        log.debug("Request to save ArtworkCategory : {}", artworkCategoryDTO);
        ArtworkCategory artworkCategory = artworkCategoryMapper.toEntity(artworkCategoryDTO);
        artworkCategory = artworkCategoryRepository.save(artworkCategory);
        return artworkCategoryMapper.toDto(artworkCategory);
    }

    @Override
    public ArtworkCategoryDTO update(ArtworkCategoryDTO artworkCategoryDTO) {
        log.debug("Request to update ArtworkCategory : {}", artworkCategoryDTO);
        ArtworkCategory artworkCategory = artworkCategoryMapper.toEntity(artworkCategoryDTO);
        artworkCategory = artworkCategoryRepository.save(artworkCategory);
        return artworkCategoryMapper.toDto(artworkCategory);
    }

    @Override
    public Optional<ArtworkCategoryDTO> partialUpdate(ArtworkCategoryDTO artworkCategoryDTO) {
        log.debug("Request to partially update ArtworkCategory : {}", artworkCategoryDTO);

        return artworkCategoryRepository
            .findById(artworkCategoryDTO.getId())
            .map(existingArtworkCategory -> {
                artworkCategoryMapper.partialUpdate(existingArtworkCategory, artworkCategoryDTO);

                return existingArtworkCategory;
            })
            .map(artworkCategoryRepository::save)
            .map(artworkCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkCategoryDTO> findAll() {
        log.debug("Request to get all ArtworkCategories");
        return artworkCategoryRepository
            .findAll()
            .stream()
            .map(artworkCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkCategory> findOne(Long id) {
        log.debug("Request to get ArtworkCategory : {}", id);
        return artworkCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkCategory : {}", id);
        artworkCategoryRepository.deleteById(id);
    }
}
