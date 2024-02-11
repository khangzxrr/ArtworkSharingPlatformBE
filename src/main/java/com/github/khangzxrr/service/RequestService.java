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
    Page<RequestDTO> findAll(Pageable pageable);
    RequestDTO create(CreateRequestDTO createRequestDTO);
    RequestDTO update(Long requestId, UpdateRequestDTO updateRequestDTO);
    void delete(Long id);
    Optional<Request> getRequestByIdAndBelongToCurrentUser(long id);

    void chooseRequestBid(long requestId, long requestBidId);

    RequestStepGuideDTO getCurrentStep(Long requestId);

    boolean isAllRequestReportSuccessed(Request request);

    List<RequestProgressAttachmentDTO> getFinishedArtworkAttachments(long requestId);
}
