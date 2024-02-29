package com.github.khangzxrr.service;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.service.dto.CreateRequestDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.dto.RequestProgressAttachmentDTO;
import com.github.khangzxrr.service.dto.UpdateRequestDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.RequestStepGuideDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.Request}.
 */
public interface RequestService {
    Page<RequestDTO> getAll(Pageable pageable);
    Optional<Request> getOne(long requestId);

    Page<RequestDTO> getAllOfUser(Pageable pageable);
    Optional<Request> getOneOfUser(long requestId);

    RequestDTO create(CreateRequestDTO createRequestDTO);
    RequestDTO update(Long requestId, UpdateRequestDTO updateRequestDTO);
    void delete(Long id);

    void chooseRequestBid(long requestId, long requestBidId);

    boolean hasAnyReport(Request request);

    RequestStepGuideDTO getCurrentStep(Long requestId);

    List<RequestProgressAttachmentDTO> getFinishedArtworkAttachments(long requestId);
}
