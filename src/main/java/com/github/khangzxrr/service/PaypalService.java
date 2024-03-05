package com.github.khangzxrr.service;

import com.github.khangzxrr.service.dto.PaypalCaptureDTO;
import com.github.khangzxrr.service.dto.PaypalOrderDTO;
import com.github.khangzxrr.service.dto.PaypalTokenDTO;

public interface PaypalService {
    PaypalTokenDTO getAccessToken();
    PaypalCaptureDTO verifyPayment(String token);
    PaypalOrderDTO createDepositOrder();
}
