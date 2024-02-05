package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.SellingBid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SellingBid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SellingBidRepository extends JpaRepository<SellingBid, Long> {}
