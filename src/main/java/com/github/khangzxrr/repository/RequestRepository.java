package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.Request;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("select request from Request request where request.user.login = ?#{authentication.name}")
    List<Request> findByUserIsCurrentUser();
}
