package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.ArtworkLike;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkLike entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkLikeRepository extends JpaRepository<ArtworkLike, Long> {
    @Query("select artworkLike from ArtworkLike artworkLike where artworkLike.owner.login = ?#{authentication.name}")
    List<ArtworkLike> findByOwnerIsCurrentUser();
}
