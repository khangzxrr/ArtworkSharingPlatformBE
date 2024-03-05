package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.repository.RequestRepository;
import com.github.khangzxrr.service.RequestProgressService;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.mapper.RequestProgressMapper;
import com.github.khangzxrr.web.rest.errors.RequestNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestProgressServiceImpl implements RequestProgressService {

    private RequestRepository requestRepository;
    private RequestProgressMapper requestProgressMapper;

    public RequestProgressServiceImpl(RequestRepository requestRepository, RequestProgressMapper requestProgressMapper) {
        this.requestRepository = requestRepository;
        this.requestProgressMapper = requestProgressMapper;
    }

    @Override
    public List<RequestProgressDTO> findAllByRequestId(Long requestId) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        return requestOptional.get().getRequestProgresses().stream().map(requestProgressMapper::toDto).collect(Collectors.toList());
    }
}
