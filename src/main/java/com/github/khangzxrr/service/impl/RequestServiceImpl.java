package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import com.github.khangzxrr.repository.RequestRepository;
import com.github.khangzxrr.service.RequestService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.dto.CreateRequestDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.dto.UpdateRequestDTO;
import com.github.khangzxrr.service.mapper.RequestBidMapper;
import com.github.khangzxrr.service.mapper.RequestMapper;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.RequestBidNotFoundException;
import com.github.khangzxrr.web.rest.errors.RequestIsNotOnCorrectState;
import com.github.khangzxrr.web.rest.errors.RequestNotFoundException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.Request}.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    private final UserService userService;

    public RequestServiceImpl(
        RequestRepository requestRepository,
        RequestMapper requestMapper,
        UserService userService,
        RequestBidMapper requestBidMapper
    ) {
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
        request.setStatus(RequestStatus.ON_BIDING);

        request = requestRepository.save(request);

        return requestMapper.toDto(request);
    }

    @Override
    public Optional<Request> getRequestByIdAndBelongToCurrentUser(long id) {
        Optional<User> user = userService.getUserWithAuthorities();
        if (!user.isPresent()) {
            return Optional.empty();
        }

        Optional<Request> request = requestRepository.findById(id);
        if (!request.isPresent()) {
            return Optional.empty();
        }

        User requestUser = request.get().getUser();

        if (requestUser != user.get()) {
            return Optional.empty();
        }

        return request;
    }

    @Override
    public RequestDTO update(Long requestId, UpdateRequestDTO updateRequestDTO) {
        log.debug("Request update Request: {}", updateRequestDTO);

        Optional<Request> requestOptional = getRequestByIdAndBelongToCurrentUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        if (requestOptional.get().getStatus() != RequestStatus.ON_BIDING) {
            throw new RequestIsNotOnCorrectState();
        }

        //workaround for coverting request-children (attachments,...)
        Request updateEntity = requestMapper.toEntity(requestMapper.toDto(updateRequestDTO));

        Request request = requestOptional.get();
        request.setDescription(updateEntity.getDescription());
        request.setAttachments(updateEntity.getAttachments());

        request = requestRepository.save(request);

        return requestMapper.toDto(request);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);

        getRequestByIdAndBelongToCurrentUser(id);

        requestRepository.deleteById(id);
    }

    @Override
    public void chooseRequestBid(Long requestId, Long requestBidId) {
        Optional<Request> requestOptional = getRequestByIdAndBelongToCurrentUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (request.getStatus() != RequestStatus.ON_BIDING) {
            throw new RequestIsNotOnCorrectState();
        }

        RequestBid selectedBid = request.getRequestBids().stream().filter(b -> b.getId() == requestBidId).findFirst().orElse(null);

        if (selectedBid == null) {
            throw new RequestBidNotFoundException();
        }

        //select this bid
        selectedBid.setStatus(RequestBidStatus.SELECTED_BID);

        //set continue state is ON_GOING
        request.setStatus(RequestStatus.ON_GOING);

        requestRepository.save(request);
    }
}
