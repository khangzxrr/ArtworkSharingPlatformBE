package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.RequestAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestAttachmentRepository extends JpaRepository<RequestAttachment, Long> {}
