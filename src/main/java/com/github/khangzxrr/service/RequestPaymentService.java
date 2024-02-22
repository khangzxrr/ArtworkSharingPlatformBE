package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.requestProgressDto.RequestProgressPaymentDTO;

public interface RequestPaymentService {
    double calculateFirstPayment(double bidPrice);
    double calculateSecondPayment(double bidPrice);
    double calculateFeeEarn(double bidPrice);

    RequestProgressPaymentDTO getFirstPayment(Long requestId);
    RequestProgressPaymentDTO getSecondPayment(Long reqquestId);

    RequestProgressPaymentDTO payFirstPayment(Long requestId);
    RequestProgressPaymentDTO paySecondPayment(Long requestId);
}
