package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import com.github.khangzxrr.repository.RequestBidRepository;
import com.github.khangzxrr.repository.RequestRepository;
import com.github.khangzxrr.service.RequestBidService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.dto.CreateRequestBidDTO;
import com.github.khangzxrr.service.dto.RequestBidDTO;
import com.github.khangzxrr.service.dto.UpdateRequestBidDTO;
import com.github.khangzxrr.service.mapper.RequestBidMapper;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.RequestBidIsNotInValidStateException;
import com.github.khangzxrr.web.rest.errors.RequestBidNotFoundException;
import com.github.khangzxrr.web.rest.errors.RequestIsBelongToCurrentUser;
import com.github.khangzxrr.web.rest.errors.RequestIsNotInCorrectState;
import com.github.khangzxrr.web.rest.errors.RequestNotFoundException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestBidServiceImpl implements RequestBidService {

    private final RequestRepository requestRepository;
    private final RequestBidRepository requestBidRepository;
    private final UserService userService;

    private final RequestBidMapper requestBidMapper;

    public RequestBidServiceImpl(
        RequestRepository requestRepository,
        RequestBidRepository requestBidRepository,
        UserService userService,
        RequestBidMapper requestBidMapper
    ) {
        this.requestRepository = requestRepository;
        this.requestBidRepository = requestBidRepository;
        this.userService = userService;
        this.requestBidMapper = requestBidMapper;
    }

    @Override
    public RequestBidDTO placeBidOnRequest(Long requestId, CreateRequestBidDTO createRequestBidDTO) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);

        Optional<User> user = userService.getUserWithAuthorities();

        // throw exception when user is not logged
        if (!user.isPresent()) {
            throw new NotLoggedException();
        }

        // throw exception when request not found
        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        // throw exception when request is owned by user
        if (request.getUser() == user.get()) {
            throw new RequestIsBelongToCurrentUser();
        }

        // throw exception when request is not on biding state
        if (request.getStatus() != RequestStatus.ON_BIDING) {
            throw new RequestIsNotInCorrectState();
        }

        RequestBid requestBid = requestBidMapper.toEntity(requestBidMapper.toDto(createRequestBidDTO));
        requestBid.setUser(user.get());
        requestBid.setStatus(RequestBidStatus.BIDED);

        request.addRequestBids(requestBid);

        requestRepository.save(request);

        return requestBidMapper.toDto(requestBid);
    }

    private Optional<RequestBid> validateRequestBidBelongToUserAndRequest(Long requestId, Long requestBidId) {
        Optional<User> userOptional = userService.getUserWithAuthorities();

        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        Optional<RequestBid> requestOptional = requestBidRepository.findByIdAndRequestId(requestBidId, requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestBidNotFoundException();
        }

        return requestOptional;
    }

    @Override
    public RequestBidDTO updateRequestBid(Long requestId, Long requestBidId, UpdateRequestBidDTO updateRequestDTO) {
        RequestBid requestBid = validateRequestBidBelongToUserAndRequest(requestId, requestBidId).get();

        // do not modify when not in bided state
        if (requestBid.getStatus() != RequestBidStatus.BIDED) {
            throw new RequestBidIsNotInValidStateException();
        }

        requestBid.setDescription(updateRequestDTO.getDescription());
        requestBid.setDuration(updateRequestDTO.getDuration());
        requestBid.setPrice(updateRequestDTO.getPrice());

        requestBid = requestBidRepository.save(requestBid);

        return requestBidMapper.toDto(requestBid);
    }

    @Override
    public Optional<RequestBidDTO> findOneRequestBid(Long requestId, Long requestBidId) {
        Optional<RequestBid> requestBid = validateRequestBidBelongToUserAndRequest(requestId, requestBidId);

        return requestBid.map(requestBidMapper::toDto);
    }

    @Override
    public Page<RequestBidDTO> findAllRequestBid(Long requestId, Pageable pageable) {
        return requestBidRepository.findAllByRequestId(requestId, pageable).map(requestBidMapper::toDto);
    }

    @Override
    public void deleteRequestBid(Long requestId, Long requestBidId) {
        RequestBid requestBid = validateRequestBidBelongToUserAndRequest(requestId, requestBidId).get();

        // only able to delete bid ưhen on_biding state
        if (requestBid.getRequest().getStatus() != RequestStatus.ON_BIDING) {
            throw new RequestIsNotInCorrectState();
        }

        requestBidRepository.delete(requestBid);
    }

    @Override
    public Optional<RequestBidDTO> findChoosed(Long requestId) {
        Optional<User> userOptional = userService.getUserWithAuthorities();

        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }

        Optional<Request> requestOptional = requestRepository.findByIdAndUserIsCurrentUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (request.getStatus() == RequestStatus.ON_BIDING) {
            throw new RequestIsNotInCorrectState();
        }

        return request
            .getRequestBids()
            .stream()
            .filter(rb -> rb.getStatus() == RequestBidStatus.SELECTED_BID)
            .findFirst()
            .map(requestBidMapper::toDto);
    }
}
