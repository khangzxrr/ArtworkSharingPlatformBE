package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.CreateRequestProgressReportDTO;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.RequestProgress}.
 */
public interface RequestProgressReportService {
    RequestProgressDTO create(long requestId, CreateRequestProgressReportDTO createRequestProgressReportDTO);
}
