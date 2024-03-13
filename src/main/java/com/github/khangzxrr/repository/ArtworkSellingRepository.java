package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingType;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkSelling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkSellingRepository extends JpaRepository<ArtworkSelling, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ) //this is important, because we must LOCK entity to perform any changes (dont want any other transaction update, reading this record)
    Optional<ArtworkSelling> findByArtworkIdAndStatusIn(Long artworkId, List<ArtworkSellingStatus> status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ArtworkSelling> findByIdAndArtworkId(Long Id, Long artworkId);
}
