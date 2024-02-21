package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.config.ApplicationProperties;
import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.domain.RequestProgress;
import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressType;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import com.github.khangzxrr.domain.enumeration.WalletTransactionStatus;
import com.github.khangzxrr.domain.enumeration.WalletTransactionType;
import com.github.khangzxrr.repository.RequestRepository;
import com.github.khangzxrr.repository.WalletRepository;
import com.github.khangzxrr.service.RequestPaymentService;
import com.github.khangzxrr.service.RequestService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.dto.requestProgressDto.RequestProgressPaymentDTO;
import com.github.khangzxrr.service.mapper.RequestProgressMapper;
import com.github.khangzxrr.web.rest.errors.NotAllRequestProgressReportFinishedException;
import com.github.khangzxrr.web.rest.errors.PaymentIsAlreadySuccessed;
import com.github.khangzxrr.web.rest.errors.RequestBidNotFoundException;
import com.github.khangzxrr.web.rest.errors.RequestIsNotInCorrectState;
import com.github.khangzxrr.web.rest.errors.RequestNotFoundException;
import com.github.khangzxrr.web.rest.errors.RequestProgressTypeIsNotValid;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestPaymentServiceImpl implements RequestPaymentService {

    private final RequestRepository requestRepository;

    private final WalletRepository walletRepository;

    private final RequestProgressMapper requestProgressMapper;

    private final WalletService walletService;

    private final RequestService requestService;

    private final ApplicationProperties applicationProperties;

    public RequestPaymentServiceImpl(
        RequestRepository requestRepository,
        RequestProgressMapper requestProgressMapper,
        WalletService walletService,
        WalletRepository walletRepository,
        RequestService requestService,
        ApplicationProperties applicationProperties
    ) {
        this.requestRepository = requestRepository;
        this.requestProgressMapper = requestProgressMapper;
        this.walletService = walletService;
        this.walletRepository = walletRepository;
        this.requestService = requestService;
        this.applicationProperties = applicationProperties;
    }

    private RequestProgressPaymentDTO getPaymentByType(long requestId, RequestProgressType type) {
        Optional<Request> requestOptional = requestRepository.findByIdAndUserIsCurrentUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (request.getStatus() != RequestStatus.ON_GOING) {
            throw new RequestIsNotInCorrectState();
        }

        Optional<RequestProgress> requestProgressOptional = request
            .getRequestProgresses()
            .stream()
            .filter(rp -> rp.getType() == type)
            .findFirst();

        //immediately return payment dto if requestProgress is present
        //otherwise create a new payment DTO base on platform policy
        if (requestProgressOptional.isPresent()) {
            return requestProgressOptional.map(requestProgressMapper::toPaymentDTO).get();
        }

        Optional<RequestBid> requestBidOptional = request
            .getRequestBids()
            .stream()
            .filter(rb -> rb.getStatus() == RequestBidStatus.SELECTED_BID)
            .findFirst();

        if (!requestBidOptional.isPresent()) {
            //this should never happen because request state = ON_GOING mean that
            //at least ONE request bid has been selected and change state to SELECTED_BID
            throw new RequestBidNotFoundException();
        }

        RequestProgressPaymentDTO requestProgressPaymentDTO;

        switch (type) {
            case FIRST_PAYMENT:
                double firstPaymentAmount = Math.ceil(
                    (requestBidOptional.get().getPrice() * applicationProperties.getArtworkConfiguration().getFirstPaymentPercent()) /
                    (double) 100
                );
                requestProgressPaymentDTO = new RequestProgressPaymentDTO(firstPaymentAmount, type, RequestProgressStatus.PENDING);
                break;
            case SECOND_PAYMENT:
                double secondPaymentAmount = Math.ceil(
                    (requestBidOptional.get().getPrice() * applicationProperties.getArtworkConfiguration().getSecondPaymentPercent()) /
                    (double) 100
                );

                requestProgressPaymentDTO = new RequestProgressPaymentDTO(secondPaymentAmount, type, RequestProgressStatus.PENDING);
                break;
            default:
                throw new RequestProgressTypeIsNotValid();
        }

        return requestProgressPaymentDTO;
    }

    private RequestProgressPaymentDTO payPayment(long requestId, RequestProgressType type) {
        Optional<Request> requestOptional = requestRepository.findByIdAndUserIsCurrentUser(requestId);
        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        //doesnt need to check user is authenticate or not because walletService getCurrentUserWallet is already
        //checked
        Wallet wallet = walletService.getCurrentUserWallet();

        RequestProgressPaymentDTO paymentDto;

        //first payment doesnt need to validate report
        if (type == RequestProgressType.FIRST_PAYMENT) {
            paymentDto = getFirstPayment(requestId);
        } else if (type == RequestProgressType.SECOND_PAYMENT) {
            paymentDto = getSecondPayment(requestId);

            //second payment must check all report finished first
            if (!requestService.isAllRequestReportSuccessed(request)) {
                throw new NotAllRequestProgressReportFinishedException();
            }

            request.setStatus(RequestStatus.ENDED); //end request if payment success
        } else {
            throw new RequestProgressTypeIsNotValid();
        }

        if (paymentDto.getStatus() == RequestProgressStatus.SUCCEED) {
            throw new PaymentIsAlreadySuccessed();
        }

        WalletTransaction walletTransaction = new WalletTransaction();

        walletTransaction.setAmount(paymentDto.getAmount());
        walletTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        walletTransaction.setType(WalletTransactionType.BUY);
        walletTransaction.createAt(LocalDate.now());

        //if not enough cash => throw exception inside addTransaction method
        wallet.addTransactions(walletTransaction);
        wallet = walletRepository.save(wallet);

        //convert payment dto to requestProgress entity
        RequestProgress requestProgress = requestProgressMapper.toEntity(paymentDto);

        requestProgress.setStatus(RequestProgressStatus.SUCCEED);
        requestProgress.setTransaction(walletTransaction);
        requestProgress.setDate(LocalDate.now());

        request.addRequestProgresses(requestProgress);
        requestRepository.save(request);

        return requestProgressMapper.toPaymentDTO(requestProgress);
    }

    @Override
    public RequestProgressPaymentDTO getFirstPayment(Long requestId) {
        return getPaymentByType(requestId, RequestProgressType.FIRST_PAYMENT);
    }

    @Override
    public RequestProgressPaymentDTO getSecondPayment(Long reqquestId) {
        return getPaymentByType(reqquestId, RequestProgressType.SECOND_PAYMENT);
    }

    @Override
    public RequestProgressPaymentDTO payFirstPayment(Long requestId) {
        return payPayment(requestId, RequestProgressType.FIRST_PAYMENT);
    }

    @Override
    public RequestProgressPaymentDTO paySecondPayment(Long requestId) {
        return payPayment(requestId, RequestProgressType.SECOND_PAYMENT);
    }
}
