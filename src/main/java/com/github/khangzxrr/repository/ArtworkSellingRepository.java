package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.ArtworkSelling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkSelling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkSellingRepository extends JpaRepository<ArtworkSelling, Long> {}
