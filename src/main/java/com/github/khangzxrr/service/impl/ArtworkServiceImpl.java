package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkCategory;
import com.github.khangzxrr.domain.ArtworkComment;
import com.github.khangzxrr.domain.ArtworkLike;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.enumeration.ArtworkStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkVisibility;
import com.github.khangzxrr.repository.ArtworkRepository;
import com.github.khangzxrr.service.ArtworkCategoryService;
import com.github.khangzxrr.service.ArtworkService;
import com.github.khangzxrr.service.CreateArtworkCommentDTO;
import com.github.khangzxrr.service.NotificationService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.CreateArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.UpdateArtworkDTO;
import com.github.khangzxrr.service.mapper.ArtworkCommentMapper;
import com.github.khangzxrr.service.mapper.ArtworkMapper;
import com.github.khangzxrr.web.rest.errors.ArtworkCategoryNotExistException;
import com.github.khangzxrr.web.rest.errors.ArtworkInPrivateException;
import com.github.khangzxrr.web.rest.errors.ArtworkIsDisabledException;
import com.github.khangzxrr.web.rest.errors.ArtworkNotFoundException;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.UserAlreadyLikeArtworkException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.github.khangzxrr.domain.Artwork}.
 */
@Service
@Transactional
public class ArtworkServiceImpl implements ArtworkService {

    private final Logger log = LoggerFactory.getLogger(ArtworkServiceImpl.class);

    private final ArtworkRepository artworkRepository;

    private final ArtworkCategoryService artworkCategoryService;

    private final ArtworkMapper artworkMapper;
    private final ArtworkCommentMapper artworkCommentMapper;

    private final UserService userService;
    private final NotificationService notificationService;

    public ArtworkServiceImpl(
        ArtworkRepository artworkRepository,
        ArtworkMapper artworkMapper,
        ArtworkCommentMapper artworkCommentMapper,
        ArtworkCategoryService artworkCategoryService,
        UserService userService,
        NotificationService notificationService
    ) {
        this.artworkRepository = artworkRepository;
        this.artworkMapper = artworkMapper;
        this.artworkCommentMapper = artworkCommentMapper;
        this.artworkCategoryService = artworkCategoryService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Override
    public ArtworkDTO save(ArtworkDTO artworkDTO) {
        log.debug("Request to save Artwork : {}", artworkDTO);
        Artwork artwork = artworkMapper.toEntity(artworkDTO);
        artwork = artworkRepository.save(artwork);
        return artworkMapper.toDto(artwork);
    }

    @Override
    public ArtworkDTO update(Long id, UpdateArtworkDTO updateArtworkDTO) {
        log.debug("Request to update Artwork : {}", updateArtworkDTO);

        Optional<Artwork> artworkOptional = artworkRepository.findById(id);

        if (!artworkOptional.isPresent()) {
            throw new ArtworkNotFoundException();
        }

        Optional<ArtworkCategory> category = artworkCategoryService.findOne(updateArtworkDTO.getCategoryId());

        if (!category.isPresent()) {
            throw new ArtworkCategoryNotExistException();
        }

        Artwork updateArtwork = artworkMapper.toEntity(updateArtworkDTO);

        Artwork artwork = artworkOptional.get();
        artwork.setArtworkAssets(updateArtwork.getArtworkAssets());
        artwork.setCategory(category.get());
        artwork.setDescription(updateArtwork.getDescription());
        artwork.setVisibility(updateArtwork.getVisibility());
        artwork.setName(updateArtwork.getName());

        artwork = artworkRepository.save(artwork);

        return artworkMapper.toDto(artwork);
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
    public ArtworkDTO save(CreateArtworkDTO createArtworkDTO) {
        log.debug("Request to save Artwork : {}", createArtworkDTO);

        Optional<User> user = userService.getUserWithAuthorities();

        if (!user.isPresent()) {
            throw new NotLoggedException();
        }

        Optional<ArtworkCategory> category = artworkCategoryService.findOne(createArtworkDTO.getCategoryId());

        if (!category.isPresent()) {
            throw new ArtworkCategoryNotExistException();
        }

        Artwork artwork = artworkMapper.toEntity(createArtworkDTO);
        artwork.setCategory(category.get());
        artwork.setOwner(user.get());
        artwork.setStatus(ArtworkStatus.ENABLE);

        artwork = artworkRepository.save(artwork);

        return artworkMapper.toDto(artwork);
    }

    @Override
    public Page<ArtworkDTO> findAllPublicArtworks(Pageable pageable) {
        return artworkRepository.findByVisibility(ArtworkVisibility.PUBLIC, pageable).map(artworkMapper::toDto);
    }

    @Override
    public Page<ArtworkDTO> findAllArtworksOfUser(Pageable pageable) {
        return artworkRepository.findByOwnerIsCurrentUser(pageable).map(artworkMapper::toDto);
    }

    @Override
    public void Like(Long id) {
        Optional<Artwork> artworkOptional = artworkRepository.findById(id);

        if (!artworkOptional.isPresent()) {
            throw new ArtworkNotFoundException();
        }

        Optional<User> userOptional = userService.getUserWithAuthorities();

        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        User user = userOptional.get();

        Artwork artwork = artworkOptional.get();

        boolean isUserLikedArtwork = artwork.getLikes().stream().anyMatch(l -> l.getOwner() == user);

        if (isUserLikedArtwork) {
            throw new UserAlreadyLikeArtworkException();
        }

        ArtworkLike like = new ArtworkLike();
        like.setOwner(user);

        artwork.addLikes(like);

        artworkRepository.save(artwork);

        notificationService.subcribeUsersToTopic(String.format("/topic/artwork/%d/comment", artwork.getId()), user);

        notificationService.sendToUsers(
            String.format("Artwork sharing platform - artwork '%s'", artwork.getName()),
            String.format("%s just likes your artwork!", user.getFirstName()),
            artwork.getOwner()
        );
    }

    @Override
    public ArtworkCommentDTO Comment(Long id, CreateArtworkCommentDTO createArtworkCommentDTO) {
        Optional<Artwork> artworkOptional = artworkRepository.findById(id);

        if (!artworkOptional.isPresent()) {
            throw new ArtworkNotFoundException();
        }

        Optional<User> userOptional = userService.getUserWithAuthorities();

        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        User user = userOptional.get();

        Artwork artwork = artworkOptional.get();

        if (artwork.getVisibility() == ArtworkVisibility.PRIVATE) {
            throw new ArtworkInPrivateException();
        }

        if (artwork.getStatus() == ArtworkStatus.DISABLE) {
            throw new ArtworkIsDisabledException();
        }

        ArtworkComment comment = new ArtworkComment();
        comment.content(createArtworkCommentDTO.getContent());
        comment.setOwner(user);

        artwork.addComments(comment);

        artworkRepository.save(artwork);

        return artworkCommentMapper.toDto(comment);
    }

    @Override
    public void Unlike(Long id) {
        Optional<Artwork> artworkOptional = artworkRepository.findById(id);

        if (!artworkOptional.isPresent()) {
            throw new ArtworkNotFoundException();
        }

        Optional<User> userOptional = userService.getUserWithAuthorities();

        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        User user = userOptional.get();

        Artwork artwork = artworkOptional.get();

        boolean isUserLikedArtwork = artwork.getLikes().stream().anyMatch(l -> l.getOwner() == user);

        if (isUserLikedArtwork) {
            throw new UserAlreadyLikeArtworkException();
        }

        ArtworkLike like = new ArtworkLike();
        like.setOwner(user);

        artwork.addLikes(like);

        artworkRepository.save(artwork);

        notificationService.unsubcribeUsersFromTopic(String.format("/topic/artwork/%d/comment", artwork.getId()), user);
    }
}
