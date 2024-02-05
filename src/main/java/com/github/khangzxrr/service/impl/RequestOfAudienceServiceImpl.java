package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import com.github.khangzxrr.repository.RequestRepository;
import com.github.khangzxrr.service.RequestOfAudienceService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.dto.CreateRequestDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.mapper.RequestMapper;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.RequestNotBelongToAudienceException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestOfAudienceServiceImpl implements RequestOfAudienceService {

    private final Logger log = LoggerFactory.getLogger(RequestOfAudienceServiceImpl.class);

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserService userService;

    public RequestOfAudienceServiceImpl(RequestRepository requestRepository, RequestMapper requestMapper, UserService userService) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.userService = userService;
    }

    @Override
    public Page<RequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all requests of audience");

        return requestRepository.findByUserIsCurrentUser(pageable).map(requestMapper::toDto);
    }

    @Override
    public RequestDTO create(CreateRequestDTO createRequestDTO) {
        log.debug("Request create Request: {}", createRequestDTO);

        RequestDTO requestDTO = requestMapper.toDto(createRequestDTO);

        Optional<User> user = userService.getUserWithAuthorities();

        if (!user.isPresent()) {
            throw new NotLoggedException();
        }

        Request request = requestMapper.toEntity(requestDTO);
        request.setUser(user.get());
        request.setStatus(RequestStatus.ON_GOING);

        request = requestRepository.save(request);

        return requestMapper.toDto(request);
    }

    public Optional<Request> getRequestByIdAndUserIsCurrentUser(long id) {
        Optional<User> user = userService.getUserWithAuthorities();
        if (!user.isPresent()) {
            throw new NotLoggedException();
        }

        Optional<Request> request = requestRepository.findByIdAndUserIsCurrentUser(id);
        if (!request.isPresent()) {
            throw new RequestNotBelongToAudienceException();
        }

        return request;
    }

    @Override
    public RequestDTO update(RequestDTO requestDTO) {
        log.debug("Request update Request: {}", requestDTO);

        getRequestByIdAndUserIsCurrentUser(requestDTO.getId());

        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);

        return requestMapper.toDto(request);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);

        getRequestByIdAndUserIsCurrentUser(id);

        requestRepository.deleteById(id);
    }
}
