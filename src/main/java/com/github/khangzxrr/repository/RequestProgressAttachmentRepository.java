package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.RequestProgressAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestProgressAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestProgressAttachmentRepository extends JpaRepository<RequestProgressAttachment, Long> {}
