package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingType;
import com.github.khangzxrr.repository.ArtworkRepository;
import com.github.khangzxrr.repository.ArtworkSellingRepository;
import com.github.khangzxrr.service.ArtworkService;
import com.github.khangzxrr.service.dto.ArtworkDTO;
import com.github.khangzxrr.service.mapper.ArtworkMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.Artwork}.
 */
@Service
@Transactional
public class ArtworkServiceImpl implements ArtworkService {

    private final Logger log = LoggerFactory.getLogger(ArtworkServiceImpl.class);

    private final ArtworkRepository artworkRepository;

    private final ArtworkMapper artworkMapper;

    private final ArtworkSellingRepository artworkSellingRepository;

    public ArtworkServiceImpl(
        ArtworkRepository artworkRepository,
        ArtworkMapper artworkMapper,
        ArtworkSellingRepository artworkSellingRepository
    ) {
        this.artworkRepository = artworkRepository;
        this.artworkMapper = artworkMapper;
        this.artworkSellingRepository = artworkSellingRepository;
    }

    @Override
    public ArtworkDTO save(ArtworkDTO artworkDTO) {
        log.debug("Request to save Artwork : {}", artworkDTO);
        Artwork artwork = artworkMapper.toEntity(artworkDTO);
        artwork = artworkRepository.save(artwork);
        return artworkMapper.toDto(artwork);
    }

    @Override
    public ArtworkDTO update(ArtworkDTO artworkDTO) {
        log.debug("Request to update Artwork : {}", artworkDTO);
        Artwork artwork = artworkMapper.toEntity(artworkDTO);
        artwork = artworkRepository.save(artwork);
        return artworkMapper.toDto(artwork);
    }

    @Override
    public Optional<ArtworkDTO> partialUpdate(ArtworkDTO artworkDTO) {
        log.debug("Request to partially update Artwork : {}", artworkDTO);

        return artworkRepository
            .findById(artworkDTO.getId())
            .map(existingArtwork -> {
                artworkMapper.partialUpdate(existingArtwork, artworkDTO);

                return existingArtwork;
            })
            .map(artworkRepository::save)
            .map(artworkMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtworkDTO> findAll() {
        log.debug("Request to get all Artworks");
        return artworkRepository.findAll().stream().map(artworkMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkDTO> findOne(Long id) {
        log.debug("Request to get Artwork : {}", id);
        return artworkRepository.findById(id).map(artworkMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Artwork : {}", id);
        artworkRepository.deleteById(id);
    }

    @Override
    public ArtworkDTO DirectSellings(ArtworkDTO artworkDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DirectSellings'");
    }

    @Override
    public ArtworkDTO updateSaleDirect(ArtworkDTO artworkDTO) {
        log.debug("Request to update Artwork : {}", artworkDTO);
        Artwork artwork = artworkMapper.toEntity(artworkDTO);

        artwork.getArtworkSelling().setType(ArtworkSellingType.DIRECT);
        artwork.getArtworkSelling().setStatus(ArtworkSellingStatus.ON_GOING);
        artworkSellingRepository.save(artwork.getArtworkSelling());
        artwork = artworkRepository.save(artwork);
        return artworkMapper.toDto(artwork);
    }

    @Override
    public void cancel(Long id) {
        Optional<Artwork> artworkafterdelteSeling = artworkRepository.findById(id);

        if (artworkafterdelteSeling.isPresent()) {
            Artwork aftercancel = artworkafterdelteSeling.get();
            long idSelling = aftercancel.getArtworkSelling().getId();
            aftercancel.setArtworkSelling(null);
            artworkSellingRepository.deleteById(idSelling);
            artworkRepository.save(aftercancel);
        }
    }
}
