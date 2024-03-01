package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.requestChatDTOs.CreateRequestChatDTO;
import com.github.khangzxrr.service.dto.requestChatDTOs.RequestChatDTO;
import java.util.List;

/**
 * Service Interface for managing {@link com.github.khangzxrr.domain.Request}.
 */
public interface RequestChatService {
    List<RequestChatDTO> getAll(long requestId);
    List<RequestChatDTO> getAllAfterId(long requestId, long requestChatId);

    RequestChatDTO create(long requestId, CreateRequestChatDTO requestChatDTO);
}
