package com.github.khangzxrr.service;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.service.dto.CreateRequestDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.dto.UpdateRequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.Request}.
 */
public interface RequestService {
    Page<RequestDTO> findAll(Pageable pageable);
    RequestDTO create(CreateRequestDTO createRequestDTO);
    RequestDTO update(Long requestId, UpdateRequestDTO updateRequestDTO);
    void delete(Long id);
    Optional<Request> getRequestByIdAndBelongToCurrentUser(long id);

    void chooseRequestBid(Long requestId, Long requestBidId);
}
