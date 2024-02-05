package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.RequestBid;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestBid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestBidRepository extends JpaRepository<RequestBid, Long> {
    @Query("select requestBid from RequestBid requestBid where requestBid.user.login = ?#{authentication.name}")
    List<RequestBid> findByUserIsCurrentUser();
}
