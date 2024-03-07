package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkComment;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.enumeration.ArtworkStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkVisibility;
import com.github.khangzxrr.repository.ArtworkCommentRepository;
import com.github.khangzxrr.repository.ArtworkRepository;
import com.github.khangzxrr.service.ArtworkCommentService;
import com.github.khangzxrr.service.CreateArtworkCommentDTO;
import com.github.khangzxrr.service.NotificationService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import com.github.khangzxrr.service.mapper.ArtworkCommentMapper;
import com.github.khangzxrr.web.rest.errors.ArtworkCommentNotBelongToUserException;
import com.github.khangzxrr.web.rest.errors.ArtworkCommentNotFoundException;
import com.github.khangzxrr.web.rest.errors.ArtworkInPrivateException;
import com.github.khangzxrr.web.rest.errors.ArtworkIsDisabledException;
import com.github.khangzxrr.web.rest.errors.ArtworkNotFoundException;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.github.khangzxrr.domain.ArtworkComment}.
 */
@Service
@Transactional
public class ArtworkCommentServiceImpl implements ArtworkCommentService {

    private final Logger log = LoggerFactory.getLogger(ArtworkCommentServiceImpl.class);

    private final ArtworkRepository artworkRepository;
    private final ArtworkCommentRepository artworkCommentRepository;
    private final ArtworkCommentMapper artworkCommentMapper;
    private final NotificationService notificationService;
    private final UserService userService;

    public ArtworkCommentServiceImpl(
        ArtworkRepository artworkRepository,
        ArtworkCommentMapper artworkCommentMapper,
        UserService userService,
        NotificationService notificationService,
        ArtworkCommentRepository artworkCommentRepository
    ) {
        this.artworkRepository = artworkRepository;
        this.artworkCommentMapper = artworkCommentMapper;
        this.artworkCommentRepository = artworkCommentRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Override
    public ArtworkCommentDTO comment(Long id, CreateArtworkCommentDTO createArtworkCommentDTO) {
        log.info("Comment on artwork id " + id);

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

        notificationService.subcribeUsersToTopic(String.format("/topic/artwork/%d", artwork.getId()), user);

        notificationService.sendToTopic(
            String.format("/topic/artwork/%d", artwork.getId()),
            String.format("Artwork sharing platform - Artwork '%s'", artwork.getName()),
            String.format("'%s' said '%s'", user.getLastName(), comment.getContent())
        );

        artworkRepository.save(artwork);

        return artworkCommentMapper.toDto(comment);
    }

    @Override
    public Page<ArtworkCommentDTO> getAll(Long artworkId, Pageable pageable) {
        return artworkCommentRepository.findAllByArtworkId(artworkId, pageable).map(artworkCommentMapper::toDto);
    }

    @Override
    public void delete(Long artworkId, Long commentId) {
        Optional<ArtworkComment> artworkCommentOptional = artworkCommentRepository.findByIdAndArtworkId(commentId, artworkId);

        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        if (!artworkCommentOptional.isPresent()) {
            throw new ArtworkCommentNotFoundException();
        }

        if (!artworkCommentOptional.get().getOwner().equals(userOptional.get())) {
            throw new ArtworkCommentNotBelongToUserException();
        }

        artworkCommentRepository.deleteById(commentId);
    }
}
