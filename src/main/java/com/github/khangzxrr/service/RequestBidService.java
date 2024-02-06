package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.CreateRequestBidDTO;
import com.github.khangzxrr.service.dto.RequestBidDTO;
import com.github.khangzxrr.service.dto.UpdateRequestBidDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestBidService {
    RequestBidDTO placeBidOnRequest(Long requestId, CreateRequestBidDTO createRequestBidDTO);
    RequestBidDTO updateRequestBid(Long requestId, Long requestBidId, UpdateRequestBidDTO updateRequestDTO);
    Optional<RequestBidDTO> findOneRequestBid(Long requestId, Long requestBidId);
    Page<RequestBidDTO> findAllRequestBid(Long requestId, Pageable pageable);
    void deleteRequestBid(Long requestId, Long requestBidId);
}
