package com.github.khangzxrr.service;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.service.dto.CreateRequestDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestOfAudienceService {
    Page<RequestDTO> findAll(Pageable pageable);
    RequestDTO create(CreateRequestDTO createRequestDTO);
    RequestDTO update(RequestDTO requestDTO);
    void delete(Long id);
    Optional<Request> getRequestByIdAndUserIsCurrentUser(long id);
}
