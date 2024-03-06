package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.enumeration.ArtworkVisibility;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artwork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    @Query("select artwork from Artwork artwork where artwork.owner.login = ?#{authentication.name}")
    Page<Artwork> findByOwnerIsCurrentUser(Pageable pageable);

    Page<Artwork> findByVisibility(ArtworkVisibility artworkVisibility, Pageable pageable);
}
