package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkLike;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.repository.ArtworkRepository;
import com.github.khangzxrr.service.ArtworkLikeService;
import com.github.khangzxrr.service.NotificationService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.web.rest.errors.ArtworkNotFoundException;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.UserAlreadyLikeArtworkException;
import java.util.Optional;
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

    private final ArtworkRepository artworkRepository;

    private final UserService userService;

    private final NotificationService notificationService;

    public ArtworkLikeServiceImpl(ArtworkRepository artworkRepository, UserService userService, NotificationService notificationService) {
        this.artworkRepository = artworkRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Override
    public void Like(Long id) {
        log.info("User likes artwork id " + id);

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

        notificationService.subcribeUsersToTopic(String.format("/topic/artwork/%d", artwork.getId()), user);

        notificationService.sendToUsers(
            String.format("Artwork sharing platform - artwork '%s'", artwork.getName()),
            String.format("%s and %d users like your artwork!", user.getFirstName(), artwork.getLikes().size()),
            artwork.getOwner()
        );
    }

    @Override
    public void Unlike(Long id) {
        log.info("User unlikes artwork id " + id);

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
