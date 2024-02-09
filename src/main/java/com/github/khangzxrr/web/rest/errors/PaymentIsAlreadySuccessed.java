package com.github.khangzxrr.web.rest.errors;

public class PaymentIsAlreadySuccessed extends BadRequestAlertException {

    public PaymentIsAlreadySuccessed() {
        super(
            ErrorConstants.REQUEST_PAYMENT_IS_ALREADY_SUCCESSED,
            "Request payment is already successed",
            "requestPayment",
            "requestPaymentIsAlreadySuccessed"
        );
    }
}
