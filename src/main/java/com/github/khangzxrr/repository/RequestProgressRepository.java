package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.RequestProgress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestProgress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestProgressRepository extends JpaRepository<RequestProgress, Long> {}
