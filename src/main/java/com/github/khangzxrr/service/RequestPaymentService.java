package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.requestProgressDto.RequestProgressPaymentDTO;

public interface RequestPaymentService {
    RequestProgressPaymentDTO getFirstPayment(Long requestId);
    RequestProgressPaymentDTO getSecondPayment(Long reqquestId);

    RequestProgressPaymentDTO payFirstPayment(Long requestId);
    RequestProgressPaymentDTO paySecondPayment(Long requestId);
}
