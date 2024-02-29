package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.domain.RequestProgress;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressType;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import com.github.khangzxrr.repository.RequestRepository;
import com.github.khangzxrr.service.RequestPaymentService;
import com.github.khangzxrr.service.RequestProgressReportService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.CreateRequestProgressReportDTO;
import com.github.khangzxrr.service.mapper.RequestProgressMapper;
import com.github.khangzxrr.web.rest.errors.CreatorIsNotSelectedInRequest;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.RequestIsNotInCorrectState;
import com.github.khangzxrr.web.rest.errors.RequestIsOwnedByUserException;
import com.github.khangzxrr.web.rest.errors.RequestNotFoundException;
import com.github.khangzxrr.web.rest.errors.RequestProgressTypeIsNotAReportException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.github.khangzxrr.domain.RequestProgress}.
 */
@Service
@Transactional
public class RequestProgressReportServiceImpl implements RequestProgressReportService {

    private final Logger log = LoggerFactory.getLogger(RequestProgressReportServiceImpl.class);

    private final RequestProgressMapper requestProgressMapper;

    private final RequestRepository requestRepository;

    private final UserService userService;

    public RequestProgressReportServiceImpl(
        RequestProgressMapper requestProgressMapper,
        RequestRepository requestRepository,
        UserService userService,
        RequestPaymentService requestPaymentService
    ) {
        this.requestProgressMapper = requestProgressMapper;
        this.requestRepository = requestRepository;
        this.userService = userService;
    }

    private Request getRequestByIdAndCreatorBid(long requestId) {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if (!userOptional.isPresent()) {
            throw new NotLoggedException();
        }
        User user = userOptional.get();

        Optional<Request> requestOptional = requestRepository.findById(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        //validate request is MUST NOT belong to creator - audience
        if (request.getUser().equals(user)) {
            throw new RequestIsOwnedByUserException();
        }

        //validate creator must be selected in bid
        Optional<RequestBid> requestBidOptional = requestOptional
            .get()
            .getRequestBids()
            .stream()
            .filter(rb -> rb.getStatus() == RequestBidStatus.SELECTED_BID && rb.getUser().equals(user))
            .findFirst();

        if (!requestBidOptional.isPresent()) {
            throw new CreatorIsNotSelectedInRequest();
        }

        return request;
    }

    @Override
    public RequestProgressDTO create(long requestId, CreateRequestProgressReportDTO createRequestProgressReportDTO) {
        //validate if this is not a report type
        if (createRequestProgressReportDTO.getType() != RequestProgressType.REPORT) {
            throw new RequestProgressTypeIsNotAReportException();
        }

        Request request = getRequestByIdAndCreatorBid(requestId);

        if (request.getStatus() != RequestStatus.ON_REPORTING && request.getStatus() != RequestStatus.ON_PAYING_SECOND) {
            throw new RequestIsNotInCorrectState();
        }

        RequestProgress requestProgress = requestProgressMapper.toEntity(createRequestProgressReportDTO);
        requestProgress.setStatus(RequestProgressStatus.SUCCEED);

        request.addRequestProgresses(requestProgress);

        //if at least one report then user can pay 2nd payment
        if (request.getRequestProgresses().size() > 0) {
            request.setStatus(RequestStatus.ON_PAYING_SECOND);
        }

        requestRepository.save(request);

        return requestProgressMapper.toDto(requestProgress);
    }
}
