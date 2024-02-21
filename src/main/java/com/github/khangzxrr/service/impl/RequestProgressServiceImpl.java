package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.config.Constants;
import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.domain.RequestProgress;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressStatus;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import com.github.khangzxrr.domain.enumeration.WalletTransactionType;
import com.github.khangzxrr.repository.RequestRepository;
import com.github.khangzxrr.service.RequestProgressReportService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.CreateRequestProgressReportDTO;
import com.github.khangzxrr.service.mapper.RequestProgressMapper;
import com.github.khangzxrr.web.rest.errors.CreatorIsNotSelectedInRequest;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.RequestIsOwnedByUserException;
import com.github.khangzxrr.web.rest.errors.RequestNotFoundException;
import com.github.khangzxrr.web.rest.errors.RequestProgressIsNotExistException;
import com.github.khangzxrr.web.rest.errors.RequestProgressReportIsExistException;
import com.github.khangzxrr.web.rest.errors.RequestProgressStatusNotInPendingException;
import com.github.khangzxrr.web.rest.errors.RequestProgressTypeIsNotAReportException;
import java.time.LocalDate;
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
public class RequestProgressServiceImpl implements RequestProgressReportService {

    private final Logger log = LoggerFactory.getLogger(RequestProgressServiceImpl.class);

    private final RequestProgressMapper requestProgressMapper;

    private final RequestRepository requestRepository;

    private final UserService userService;

    private final WalletService walletService;

    public RequestProgressServiceImpl(
        RequestProgressMapper requestProgressMapper,
        RequestRepository requestRepository,
        UserService userService,
        WalletService walletService
    ) {
        this.requestProgressMapper = requestProgressMapper;
        this.requestRepository = requestRepository;
        this.userService = userService;
        this.walletService = walletService;
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
        if (!Constants.REQUEST_PROGRESS_REPORT_TYPES.contains(createRequestProgressReportDTO.getType())) {
            throw new RequestProgressTypeIsNotAReportException();
        }

        Request request = getRequestByIdAndCreatorBid(requestId);

        Optional<RequestProgress> requestProgressOptional = request
            .getRequestProgresses()
            .stream()
            .filter(rp -> rp.getType() == createRequestProgressReportDTO.getType())
            .findFirst();

        if (requestProgressOptional.isPresent()) {
            throw new RequestProgressReportIsExistException();
        }

        RequestProgress requestProgress = requestProgressMapper.toEntity(createRequestProgressReportDTO);
        requestProgress.setStatus(RequestProgressStatus.PENDING);
        requestProgress.setDate(LocalDate.now());

        request.addRequestProgresses(requestProgress);

        requestRepository.save(request);

        return requestProgressMapper.toDto(requestProgress);
    }

    @Override
    public void accept(long requestId, long requestProgressId) {
        log.debug("accept state - of request id {} progress id {}", requestId, requestProgressId);

        Optional<Request> requestOptional = requestRepository.findByIdAndUserIsCurrentUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        //find by ID and MUST be REPORT type
        Optional<RequestProgress> requestProgressOptional = request
            .getRequestProgresses()
            .stream()
            .filter(rp -> rp.getId() == requestProgressId && Constants.REQUEST_PROGRESS_REPORT_TYPES.contains(rp.getType()))
            .findFirst();

        if (!requestProgressOptional.isPresent()) {
            throw new RequestProgressIsNotExistException();
        }

        RequestProgress requestProgress = requestProgressOptional.get();

        if (requestProgress.getStatus() != RequestProgressStatus.PENDING) {
            throw new RequestProgressStatusNotInPendingException();
        }

        requestProgress.setStatus(RequestProgressStatus.SUCCEED);

        requestRepository.save(request);
    }

    @Override
    public void reject(long requestId, long requestProgressId) {
        log.debug("reject state - of request id {} progress id {}", requestId, requestProgressId);

        Optional<Request> requestOptional = requestRepository.findByIdAndUserIsCurrentUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        //find by ID and MUST be REPORT type
        Optional<RequestProgress> requestProgressOptional = request
            .getRequestProgresses()
            .stream()
            .filter(rp -> rp.getId() == requestProgressId && Constants.REQUEST_PROGRESS_REPORT_TYPES.contains(rp.getType()))
            .findFirst();

        if (!requestProgressOptional.isPresent()) {
            throw new RequestProgressIsNotExistException();
        }

        RequestProgress requestProgress = requestProgressOptional.get();

        if (requestProgress.getStatus() != RequestProgressStatus.PENDING) {
            throw new RequestProgressStatusNotInPendingException();
        }

        requestProgress.setStatus(RequestProgressStatus.FAILED);

        //failed request progress report = failed request
        request.setStatus(RequestStatus.FAILED);

        Wallet wallet = walletService.getCurrentUserWallet();

        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setType(WalletTransactionType.REFUND);
        walletTransaction.setAmount(0d);
        walletTransaction.setCreateAt(LocalDate.now());

        wallet.addTransactions(walletTransaction);

        requestRepository.save(request);
    }
}
