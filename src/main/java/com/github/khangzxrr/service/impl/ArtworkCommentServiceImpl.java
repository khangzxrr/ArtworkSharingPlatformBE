package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.ArtworkComment;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.repository.ArtworkCommentRepository;
import com.github.khangzxrr.service.ArtworkCommentService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import com.github.khangzxrr.service.mapper.ArtworkCommentMapper;
import com.github.khangzxrr.web.rest.errors.BadRequestIDAlertException;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import java.time.LocalDate;
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

    private final UserService userService;

    public ArtworkCommentServiceImpl(
        ArtworkCommentRepository artworkCommentRepository,
        ArtworkCommentMapper artworkCommentMapper,
        UserService userService
    ) {
        this.artworkCommentRepository = artworkCommentRepository;
        this.artworkCommentMapper = artworkCommentMapper;
        this.userService = userService;
    }

    @Override
    public ArtworkCommentDTO save(ArtworkCommentDTO artworkCommentDTO) {
        log.debug("Request to save ArtworkComment : {}", artworkCommentDTO);
        ArtworkComment artworkComment = artworkCommentMapper.toEntity(artworkCommentDTO);

        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }
        artworkComment.setOwner(userOptional.get());
        artworkComment.setCreateAt(LocalDate.now());

        artworkComment = artworkCommentRepository.save(artworkComment);
        return artworkCommentMapper.toDto(artworkComment);
    }

    @Override
    public ArtworkCommentDTO update(ArtworkCommentDTO artworkCommentDTO) {
        log.debug("Request to update ArtworkComment : {}", artworkCommentDTO);
        ArtworkComment artworkComment = artworkCommentMapper.toEntity(artworkCommentDTO);

        Long cmtid = artworkComment.getId();

        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        artworkComment.setOwner(userOptional.get());

        checkOwner(cmtid, userOptional.get());

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

        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        checkOwner(id, userOptional.get());

        artworkCommentRepository.deleteById(id);
    }

    private void checkOwner(Long cmtid, User currentUser) {
        ArtworkComment artworkComment = artworkCommentRepository
            .findById(cmtid)
            .orElseThrow(() -> new BadRequestIDAlertException("Comment not found with ID: ", cmtid, "Comment_not_found"));

        if (!artworkComment.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestIDAlertException("Current user is not the owner of ArtworkComment with ID: ", cmtid, "Owner_not_match");
        }
    }
}
