package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.CreateRequestProgressReportDTO;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.RequestProgress}.
 */
public interface RequestProgressReportService {
    void accept(long requestId, long requestProgressId);
    void reject(long requestId, long requestProgressId);
    RequestProgressDTO create(long requestId, CreateRequestProgressReportDTO createRequestProgressReportDTO);
}
