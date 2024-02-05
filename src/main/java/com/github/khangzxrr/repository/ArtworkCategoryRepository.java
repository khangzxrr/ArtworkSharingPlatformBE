package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.ArtworkCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ArtworkCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtworkCategoryRepository extends JpaRepository<ArtworkCategory, Long> {}
