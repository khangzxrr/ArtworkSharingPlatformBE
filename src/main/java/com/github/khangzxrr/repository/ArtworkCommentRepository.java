package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.ArtworkComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkCommentRepository extends JpaRepository<ArtworkComment, Long> {
    @Query("select artworkComment from ArtworkComment artworkComment where artworkComment.owner.login = ?#{authentication.name}")
    List<ArtworkComment> findByOwnerIsCurrentUser();

    Page<ArtworkComment> findAllByArtworkId(Long artworkId, Pageable pageable);

    Optional<ArtworkComment> findByIdAndArtworkId(Long id, Long artworkId);
}
