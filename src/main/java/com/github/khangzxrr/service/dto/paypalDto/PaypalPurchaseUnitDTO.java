package com.github.khangzxrr.service.dto.paypalDto;

public class PaypalPurchaseUnitDTO {

    private PaypalPaymentDTO payments;

    public PaypalPaymentDTO getPayments() {
        return payments;
    }

    public void setPayments(PaypalPaymentDTO payments) {
        this.payments = payments;
    }
}
