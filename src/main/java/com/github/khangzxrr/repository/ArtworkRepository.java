package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkSelling;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artwork entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    @Query("select artwork from Artwork artwork where artwork.owner.login = ?#{authentication.name}")
    List<Artwork> findByOwnerIsCurrentUser();

    @Query(value = "SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM ArtworkSelling a WHERE a.artwork.id = :artworkId")
    boolean existsArtworkSellingByArtworkId(Long artworkId);
}
