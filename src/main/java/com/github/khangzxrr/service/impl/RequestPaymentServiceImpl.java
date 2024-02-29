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
import com.github.khangzxrr.service.RequestPaymentService;
import com.github.khangzxrr.service.RequestService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.dto.requestProgressDto.RequestProgressPaymentDTO;
import com.github.khangzxrr.service.mapper.RequestProgressMapper;
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

    private final RequestProgressMapper requestProgressMapper;

    private final WalletService walletService;

    private final RequestService requestService;

    private final ApplicationProperties applicationProperties;

    public RequestPaymentServiceImpl(
        RequestRepository requestRepository,
        RequestProgressMapper requestProgressMapper,
        WalletService walletService,
        RequestService requestService,
        ApplicationProperties applicationProperties
    ) {
        this.requestRepository = requestRepository;
        this.requestProgressMapper = requestProgressMapper;
        this.walletService = walletService;
        this.requestService = requestService;
        this.applicationProperties = applicationProperties;
    }

    private double getServiceFeeEarnByRequestId(long requestId) {
        Optional<Request> requestOptional = requestRepository.findByIdAndUserIsCurrentUser(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        Optional<RequestBid> requestBidOptional = request
            .getRequestBids()
            .stream()
            .filter(rb -> rb.getStatus() == RequestBidStatus.SELECTED_BID)
            .findFirst();

        if (!requestBidOptional.isPresent()) {
            // this should never happen because request state = ON_GOING mean that
            // at least ONE request bid has been selected and change state to SELECTED_BID
            throw new RequestBidNotFoundException();
        }

        return calculateFeeEarn(requestBidOptional.get().getPrice());
    }

    private RequestProgressPaymentDTO getPaymentByType(long requestId, RequestProgressType type) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (request.getStatus() == RequestStatus.ON_BIDING) {
            throw new RequestIsNotInCorrectState();
        }

        Optional<RequestProgress> requestProgressOptional = request
            .getRequestProgresses()
            .stream()
            .filter(rp -> rp.getType() == type)
            .findFirst();

        // immediately return payment dto if requestProgress is present
        // otherwise create a new payment DTO base on platform policy
        if (requestProgressOptional.isPresent()) {
            return requestProgressOptional.map(requestProgressMapper::toPaymentDTO).get();
        }

        Optional<RequestBid> requestBidOptional = request
            .getRequestBids()
            .stream()
            .filter(rb -> rb.getStatus() == RequestBidStatus.SELECTED_BID)
            .findFirst();

        if (!requestBidOptional.isPresent()) {
            // this should never happen because request state = ON_GOING mean that
            // at least ONE request bid has been selected and change state to SELECTED_BID
            throw new RequestBidNotFoundException();
        }

        RequestProgressPaymentDTO requestProgressPaymentDTO;

        switch (type) {
            case FIRST_PAYMENT:
                double firstPaymentPrice = calculateFirstPayment(requestBidOptional.get().getPrice());
                requestProgressPaymentDTO = new RequestProgressPaymentDTO(firstPaymentPrice, type, RequestProgressStatus.PENDING);

                break;
            case SECOND_PAYMENT:
                double secondPaymentPrice = calculateSecondPayment(requestBidOptional.get().getPrice());
                double serviceFeeEarnPrice = calculateFeeEarn(requestBidOptional.get().getPrice());

                double secondPaymentAmount = secondPaymentPrice + serviceFeeEarnPrice; // second payment must pay fee
                // earn too

                requestProgressPaymentDTO = new RequestProgressPaymentDTO(secondPaymentAmount, type, RequestProgressStatus.PENDING);

                break;
            default:
                throw new RequestProgressTypeIsNotValid();
        }

        return requestProgressPaymentDTO;
    }

    @Override
    public RequestProgressPaymentDTO getFirstPayment(long requestId) {
        return getPaymentByType(requestId, RequestProgressType.FIRST_PAYMENT);
    }

    @Override
    public RequestProgressPaymentDTO getSecondPayment(long reqquestId) {
        return getPaymentByType(reqquestId, RequestProgressType.SECOND_PAYMENT);
    }

    @Override
    public RequestProgressPaymentDTO payFirstPayment(long requestId) {
        Optional<Request> requestOptional = requestRepository.findByIdAndUserIsCurrentUser(requestId);
        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (request.getStatus() != RequestStatus.ON_PAYING_FIRST) {
            throw new RequestIsNotInCorrectState();
        }

        // doesnt need to check user is authenticate or not because walletService
        // getCurrentUserWallet is already
        // checked
        Wallet wallet = walletService.getCurrentUserWallet();

        RequestProgressPaymentDTO paymentDto;

        paymentDto = getFirstPayment(requestId);

        if (paymentDto.getStatus() == RequestProgressStatus.SUCCEED) {
            throw new PaymentIsAlreadySuccessed();
        }

        WalletTransaction walletTransaction = new WalletTransaction();

        walletTransaction.setAmount(paymentDto.getAmount());
        walletTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        walletTransaction.setType(WalletTransactionType.BUY);
        walletTransaction.createAt(LocalDate.now());

        // if not enough cash => throw exception inside addTransaction method
        wallet.addTransactions(walletTransaction);
        walletService.save(wallet);

        // deposit temping money to admin wallet
        Wallet adminWallet = walletService.getAdminWallet();

        WalletTransaction serviceFeeEarnTransaction = new WalletTransaction();
        serviceFeeEarnTransaction.setAmount(walletTransaction.getAmount()); // get from user transaction is a
        // work-round, becareful
        serviceFeeEarnTransaction.setType(WalletTransactionType.REQUEST_FIRST_PAYMENT_TEMP);
        serviceFeeEarnTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        serviceFeeEarnTransaction.setCreateAt(LocalDate.now());

        adminWallet.addTransactions(serviceFeeEarnTransaction);
        walletService.save(adminWallet);

        // ===============

        // convert payment dto to requestProgress entity
        RequestProgress requestProgress = requestProgressMapper.toEntity(paymentDto);

        requestProgress.setStatus(RequestProgressStatus.SUCCEED);
        requestProgress.setTransaction(walletTransaction);
        requestProgress.setDate(LocalDate.now());

        //set request to next on payment
        request.setStatus(RequestStatus.ON_REPORTING);

        request.addRequestProgresses(requestProgress);
        requestRepository.save(request);

        return requestProgressMapper.toPaymentDTO(requestProgress);
    }

    @Override
    public RequestProgressPaymentDTO paySecondPayment(long requestId) {
        Optional<Request> requestOptional = requestRepository.findByIdAndUserIsCurrentUser(requestId);
        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (request.getStatus() != RequestStatus.ON_PAYING_SECOND) {
            throw new RequestIsNotInCorrectState();
        }

        Optional<RequestBid> requestBidOptional = request
            .getRequestBids()
            .stream()
            .filter(rb -> rb.getStatus() == RequestBidStatus.SELECTED_BID)
            .findFirst();

        if (!requestBidOptional.isPresent()) {
            // this should never happen because request state = ON_GOING mean that
            // at least ONE request bid has been selected and change state to SELECTED_BID
            throw new RequestBidNotFoundException();
        }
        RequestBid requestBid = requestBidOptional.get();

        // doesnt need to check user is authenticate or not because walletService
        // getCurrentUserWallet is already
        // checked
        Wallet wallet = walletService.getCurrentUserWallet();
        Wallet adminWallet = walletService.getAdminWallet();
        Wallet creatorWallet = walletService.getWalletByUserLogin(requestBid.getUser().getLogin());

        RequestProgressPaymentDTO paymentDto;

        paymentDto = getSecondPayment(requestId);

        request.setStatus(RequestStatus.ENDED); // end request if payment success

        if (paymentDto.getStatus() == RequestProgressStatus.SUCCEED) {
            throw new PaymentIsAlreadySuccessed();
        }

        // withdraw money from request owner
        WalletTransaction walletTransaction = new WalletTransaction();

        walletTransaction.setAmount(paymentDto.getAmount());
        walletTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        walletTransaction.setType(WalletTransactionType.BUY);
        walletTransaction.createAt(LocalDate.now());

        // if not enough cash => throw exception inside addTransaction method
        wallet.addTransactions(walletTransaction);
        walletService.save(wallet);

        // ===========================================================

        // deposit service fee money to admin wallet
        // withdraw temping first payment from admin wallet

        WalletTransaction serviceFeeEarnTransaction = new WalletTransaction();

        serviceFeeEarnTransaction.setAmount(getServiceFeeEarnByRequestId(requestId));
        serviceFeeEarnTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        serviceFeeEarnTransaction.setType(WalletTransactionType.SERVICE_FEE_EARN);
        serviceFeeEarnTransaction.createAt(LocalDate.now());

        WalletTransaction withdrawTempingFirstPaymentTransaction = new WalletTransaction();
        withdrawTempingFirstPaymentTransaction.setAmount(calculateFirstPayment(requestBid.getPrice()));
        withdrawTempingFirstPaymentTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        withdrawTempingFirstPaymentTransaction.setType(WalletTransactionType.WITHDRAW_REQUEST_FIRST_PAYMENT_TEMP);
        withdrawTempingFirstPaymentTransaction.createAt(LocalDate.now());

        adminWallet.addTransactions(serviceFeeEarnTransaction);
        adminWallet.addTransactions(withdrawTempingFirstPaymentTransaction);

        walletService.save(adminWallet);
        // ==================================================

        //deposit request's money to creator wallet
        WalletTransaction creatorEarnTransaction = new WalletTransaction();
        creatorEarnTransaction.setAmount(requestBid.getPrice()); //work-round to get full price
        creatorEarnTransaction.setType(WalletTransactionType.REQUEST_EARN);
        creatorEarnTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        creatorEarnTransaction.createAt(LocalDate.now());

        creatorWallet.addTransactions(creatorEarnTransaction);
        walletService.save(creatorWallet);

        //===================================================

        // convert payment dto to requestProgress entity
        RequestProgress requestProgress = requestProgressMapper.toEntity(paymentDto);

        requestProgress.setStatus(RequestProgressStatus.SUCCEED);
        requestProgress.setTransaction(walletTransaction);
        requestProgress.setDate(LocalDate.now());

        request.addRequestProgresses(requestProgress);
        requestRepository.save(request);

        return requestProgressMapper.toPaymentDTO(requestProgress);
    }

    @Override
    public double calculateFirstPayment(double bidPrice) {
        double firstPaymentPercent = applicationProperties.getArtworkConfiguration().getFirstPaymentPercent();

        return Math.ceil((bidPrice * firstPaymentPercent) / 100.0d);
    }

    @Override
    public double calculateSecondPayment(double bidPrice) {
        double secondPaymentPercent = applicationProperties.getArtworkConfiguration().getSecondPaymentPercent();

        return Math.ceil((bidPrice * secondPaymentPercent) / 100.0d);
    }

    @Override
    public double calculateFeeEarn(double bidPrice) {
        double serviceFeeEarnPercent = applicationProperties.getArtworkConfiguration().getServiceFeeEarnPercent();

        return Math.ceil((bidPrice * serviceFeeEarnPercent) / 100.0d);
    }

    @Override
    public double calculateRefund(double bidPrice) {
        double refundPercent = applicationProperties.getArtworkConfiguration().getRefundPercent();

        return Math.ceil((bidPrice * refundPercent) / 100.0d);
    }

    @Override
    public void refund(long requestId) {
        Optional<Request> requestOptional = requestRepository.findByIdAndUserIsCurrentUser(requestId);
        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (request.getStatus() != RequestStatus.ON_REPORTING) {
            throw new RequestIsNotInCorrectState();
        }

        Optional<RequestBid> requestBidOptional = request
            .getRequestBids()
            .stream()
            .filter(rb -> rb.getStatus() == RequestBidStatus.SELECTED_BID)
            .findFirst();

        if (!requestBidOptional.isPresent()) {
            // this should never happen because request state = ON_GOING mean that
            // at least ONE request bid has been selected and change state to SELECTED_BID
            throw new RequestBidNotFoundException();
        }
        RequestBid requestBid = requestBidOptional.get();

        // doesnt need to check user is authenticate or not because walletService
        // getCurrentUserWallet is already
        // checked
        Wallet userWallet = walletService.getCurrentUserWallet();
        Wallet adminWallet = walletService.getAdminWallet();

        double refundPrice = calculateRefund(requestBid.getPrice());

        //withdraw refund price of first payment temping in admin wallet

        WalletTransaction withdrawRefundFromFirstPaymentTransaction = new WalletTransaction();
        withdrawRefundFromFirstPaymentTransaction.amount(refundPrice);
        withdrawRefundFromFirstPaymentTransaction.setType(WalletTransactionType.WITHDRAW_REFUND_REQUEST_FIRST_PAYMENT_TEMP);
        withdrawRefundFromFirstPaymentTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        withdrawRefundFromFirstPaymentTransaction.setCreateAt(LocalDate.now());
        adminWallet.addTransactions(withdrawRefundFromFirstPaymentTransaction);

        walletService.save(adminWallet);
        //===================================
        // refund to request owner
        WalletTransaction refundToRequestOwnerTransaction = new WalletTransaction();

        refundToRequestOwnerTransaction.setAmount(refundPrice);
        refundToRequestOwnerTransaction.setStatus(WalletTransactionStatus.SUCCEED);
        refundToRequestOwnerTransaction.setType(WalletTransactionType.REFUND);
        refundToRequestOwnerTransaction.createAt(LocalDate.now());
        userWallet.addTransactions(refundToRequestOwnerTransaction);

        walletService.save(userWallet);
    }
}
