package com.github.khangzxrr.repository;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestBid;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface RequestBidRepository extends JpaRepository<RequestBid, Long> {
    Page<RequestBid> findAllByRequestId(Long requestId, Pageable pageable);

    Optional<RequestBid> findByIdAndRequestId(Long id, Long requestId);
}
