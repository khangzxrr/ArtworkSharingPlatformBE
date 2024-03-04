package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.domain.RequestProgress;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressType;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import com.github.khangzxrr.domain.enumeration.WalletTransactionStatus;
import com.github.khangzxrr.domain.enumeration.WalletTransactionType;
import com.github.khangzxrr.repository.RequestRepository;
import com.github.khangzxrr.service.NotificationService;
import com.github.khangzxrr.service.RequestService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.dto.CreateRequestDTO;
import com.github.khangzxrr.service.dto.RefundDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.dto.RequestProgressAttachmentDTO;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.dto.UpdateRequestDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.RequestStepGuideDTO;
import com.github.khangzxrr.service.mapper.RequestBidMapper;
import com.github.khangzxrr.service.mapper.RequestMapper;
import com.github.khangzxrr.service.mapper.RequestProgressMapper;
import com.github.khangzxrr.web.rest.errors.DayLeftMustPositiveException;
import com.github.khangzxrr.web.rest.errors.NoRequestReportException;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.NotPaidSecondPaymentYetException;
import com.github.khangzxrr.web.rest.errors.RequestBidNotFoundException;
import com.github.khangzxrr.web.rest.errors.RequestIsNotInCorrectState;
import com.github.khangzxrr.web.rest.errors.RequestNotFoundException;
import com.github.khangzxrr.web.rest.errors.RequestProgressIsNotExistException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.github.khangzxrr.domain.Request}.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    private final UserService userService;

    private final RequestProgressMapper requestProgressMapper;

    private final WalletService walletService;

    private final SimpMessageSendingOperations messagingTemplate;

    private final NotificationService notificationService;

    public RequestServiceImpl(
        RequestRepository requestRepository,
        RequestMapper requestMapper,
        UserService userService,
        RequestBidMapper requestBidMapper,
        RequestProgressMapper requestProgressMapper,
        WalletService walletService,
        SimpMessageSendingOperations messagingTemplate,
        NotificationService notificationService
    ) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.userService = userService;
        this.requestProgressMapper = requestProgressMapper;
        this.walletService = walletService;
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    @Override
    public Page<RequestDTO> getAllOfUser(Pageable pageable) {
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
    public Optional<Request> getOneOfUser(long requestId) {
        Optional<User> user = userService.getUserWithAuthorities();
        if (!user.isPresent()) {
            return Optional.empty();
        }

        Optional<Request> request = requestRepository.findById(requestId);
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

        Optional<Request> requestOptional = getOneOfUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        if (requestOptional.get().getStatus() != RequestStatus.ON_BIDING) {
            throw new RequestIsNotInCorrectState();
        }

        // workaround for coverting request-children (attachments,...)
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

        getOneOfUser(id);

        requestRepository.deleteById(id);
    }

    @Override
    public void chooseRequestBid(long requestId, long requestBidId) {
        Optional<Request> requestOptional = getOneOfUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (request.getStatus() != RequestStatus.ON_BIDING) {
            throw new RequestIsNotInCorrectState();
        }

        Optional<RequestBid> selectedBidOptional = request
            .getRequestBids()
            .stream()
            .peek(b -> log.info("Bid id: {}", b.getId()))
            .filter(b -> b.getId() == requestBidId)
            .findFirst();

        if (!selectedBidOptional.isPresent()) {
            throw new RequestBidNotFoundException();
        }

        // select this bid
        selectedBidOptional.get().setStatus(RequestBidStatus.SELECTED_BID);

        // set continue state is ON_GOING
        request.setStatus(RequestStatus.ON_PAYING_FIRST);

        requestRepository.save(request);

        try {
            messagingTemplate.convertAndSend("/topic/requests/" + requestId + "/notification", "choosedRequestBid");

            Map<String, String> data = new HashMap<>();

            data.put("body", "audience choosed YOUR DEAL!");

            notificationService.sendToUser(data, selectedBidOptional.get().getUser());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    @Override
    public RequestStepGuideDTO getCurrentStep(Long requestId) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        RequestProgressType currentRequestProgressType = RequestProgressType.NO_ACTION_LEFT;

        RequestProgressType[] requestProgressTypes = RequestProgressType.values();

        for (RequestProgressType requestProgressType : requestProgressTypes) {
            boolean isMatchedType = requestOptional
                .get()
                .getRequestProgresses()
                .parallelStream()
                .anyMatch(rp -> rp.getType() == requestProgressType);

            if (isMatchedType) continue;

            // if not match with type => this type is not exist yet => this is the NEXT
            // STEP, break the loop
            currentRequestProgressType = requestProgressType;
            break;
        }

        // return next request type
        return new RequestStepGuideDTO(currentRequestProgressType, requestProgressTypes);
    }

    @Override
    public List<RequestProgressAttachmentDTO> getFinishedArtworkAttachments(long requestId) {
        Optional<Request> requestOptional = getOneOfUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (!hasAnyReport(request)) {
            throw new NoRequestReportException();
        }

        Optional<RequestProgress> secondPaymentRequestProgress = request
            .getRequestProgresses()
            .stream()
            .filter(rp -> rp.getType() == RequestProgressType.SECOND_PAYMENT && rp.getStatus() == RequestProgressStatus.SUCCEED)
            .findFirst();

        if (!secondPaymentRequestProgress.isPresent()) {
            throw new NotPaidSecondPaymentYetException();
        }

        RequestProgress lastRequestProgressReport = request
            .getRequestProgresses()
            .stream()
            .filter(rp -> rp.getType() == RequestProgressType.REPORT)
            .reduce((first, second) -> second)
            .get();

        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(lastRequestProgressReport);

        return requestProgressDTO.getAttachments();
    }

    @Override
    public Page<RequestDTO> getAll(Pageable pageable) {
        return requestRepository.findAll(pageable).map(requestMapper::toDto);
    }

    @Override
    public Optional<Request> getOne(long requestId) {
        return requestRepository.findById(requestId);
    }

    @Override
    public boolean hasAnyReport(Request request) {
        return request.getRequestProgresses().stream().anyMatch(rp -> rp.getType() == RequestProgressType.REPORT);
    }

    @Override
    public RefundDTO refund(long requestId) {
        Optional<Request> requestOptional = getOneOfUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if ((request.getStatus() != RequestStatus.ON_PAYING_SECOND) && (request.getStatus() != RequestStatus.ON_REPORTING)) {
            throw new RequestIsNotInCorrectState();
        }

        Wallet adminWallet = walletService.getAdminWallet();
        Wallet userWallet = walletService.getCurrentUserWallet();

        Optional<RequestBid> selectedBid = request.getSelectedBid();

        if (!selectedBid.isPresent()) {
            throw new RequestBidNotFoundException();
        }

        Optional<RequestProgress> requestFirstPaymentProgress = request
            .getRequestProgresses()
            .stream()
            .filter(rp -> rp.getType() == RequestProgressType.FIRST_PAYMENT)
            .findFirst();

        if (!requestFirstPaymentProgress.isPresent()) {
            throw new RequestProgressIsNotExistException();
        }

        Duration durationToCurrentDate = Duration.between(requestFirstPaymentProgress.get().getCreatedDate(), Instant.now());

        long onGoingDays = durationToCurrentDate.toDays();

        Long dayLefts = selectedBid.get().getDuration() - onGoingDays;

        if (dayLefts == 0) {
            throw new DayLeftMustPositiveException();
        }

        //duration 10 days
        //on-going 5 days

        //left 1 days

        //price = 100$
        //refund = price * (7/12)

        double firstPaymentAmount = requestFirstPaymentProgress.get().getTransaction().getAmount();
        double refundAmount = (firstPaymentAmount * dayLefts.doubleValue()) / selectedBid.get().getDuration().doubleValue();

        //round to 2 decimals
        refundAmount = Math.round(refundAmount * 100);
        refundAmount = refundAmount / 100;

        WalletTransaction withdrawlRefundMoneyTransaction = new WalletTransaction();
        withdrawlRefundMoneyTransaction.setAmount(refundAmount);
        withdrawlRefundMoneyTransaction.setCreateAt(LocalDate.now());
        withdrawlRefundMoneyTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        withdrawlRefundMoneyTransaction.setType(WalletTransactionType.WITHDRAW_REFUND_REQUEST_FIRST_PAYMENT_TEMP);

        WalletTransaction refundTransaction = new WalletTransaction();
        refundTransaction.setAmount(refundAmount);
        refundTransaction.setCreateAt(LocalDate.now());
        refundTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        refundTransaction.setType(WalletTransactionType.REFUND);

        adminWallet.addTransactions(withdrawlRefundMoneyTransaction);
        userWallet.addTransactions(refundTransaction);

        request.setStatus(RequestStatus.FAILED);

        walletService.save(userWallet);
        walletService.save(adminWallet);

        requestRepository.save(request);

        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setDayPassed(onGoingDays);
        refundDTO.setFirstPaymentAmount(firstPaymentAmount);
        refundDTO.setRefundAmount(refundAmount);

        try {
            messagingTemplate.convertAndSend("/topic/requests/" + requestId + "/notification", refundAmount);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return refundDTO;
    }
}
