package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.Certificate;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Certificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    @Query("select certificate from Certificate certificate where certificate.user.login = ?#{authentication.name}")
    List<Certificate> findByUserIsCurrentUser();
}
