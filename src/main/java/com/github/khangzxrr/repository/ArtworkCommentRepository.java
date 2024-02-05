package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.ArtworkComment;
import java.util.List;
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
}
