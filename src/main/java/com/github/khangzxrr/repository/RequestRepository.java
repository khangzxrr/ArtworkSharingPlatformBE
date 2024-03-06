package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("select request from Request request where request.user.login = ?#{authentication.name}")
    Page<Request> findByUserIsCurrentUser(Pageable pageable);

    @Query("select request from Request request where request.id = ?1 and request.user.login = ?#{authentication.name}")
    Optional<Request> findByIdAndUserIsCurrentUser(Long id);

    List<Request> findByStatusIn(List<RequestStatus> requestStatus);
}
