package com.github.khangzxrr.web.rest.errors;

public class NotPaidSecondPaymentYetException extends BadRequestAlertException {

    public NotPaidSecondPaymentYetException() {
        super("Not paid second payment yet", "requestProgressPayment", "notPaidSecondPaymentYet");
    }
}
