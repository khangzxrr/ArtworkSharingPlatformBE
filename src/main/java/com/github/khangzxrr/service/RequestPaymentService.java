package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.requestProgressDto.RequestProgressPaymentDTO;

public interface RequestPaymentService {
    double calculateFirstPayment(double bidPrice);
    double calculateSecondPayment(double bidPrice);
    double calculateFeeEarn(double bidPrice);

    RequestProgressPaymentDTO getFirstPayment(long requestId);
    RequestProgressPaymentDTO getSecondPayment(long reqquestId);

    RequestProgressPaymentDTO payFirstPayment(long requestId);
    RequestProgressPaymentDTO paySecondPayment(long requestId);
}
