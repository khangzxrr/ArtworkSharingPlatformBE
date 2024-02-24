package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.RequestProgressDTO;
import java.util.List;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.RequestProgress}.
 */
public interface RequestProgressService {
    List<RequestProgressDTO> findAllByRequestId(Long requestId);
}
