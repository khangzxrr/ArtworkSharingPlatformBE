package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.ArtworkAsset;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkAsset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkAssetRepository extends JpaRepository<ArtworkAsset, Long> {}
