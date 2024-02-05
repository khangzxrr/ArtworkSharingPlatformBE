package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.ArtworkComplain;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkComplain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkComplainRepository extends JpaRepository<ArtworkComplain, Long> {
    @Query("select artworkComplain from ArtworkComplain artworkComplain where artworkComplain.user.login = ?#{authentication.name}")
    List<ArtworkComplain> findByUserIsCurrentUser();
}
