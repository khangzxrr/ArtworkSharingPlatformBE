package com.github.khangzxrr.service.dto;

public class RefundDTO {

    public double firstPaymentAmount;
    public double refundAmount;
    public long dayPassed;

    public double getFirstPaymentAmount() {
        return firstPaymentAmount;
    }

    public void setFirstPaymentAmount(double firstPaymentAmount) {
        this.firstPaymentAmount = firstPaymentAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public long getDayPassed() {
        return dayPassed;
    }

    public void setDayPassed(long dayPassed) {
        this.dayPassed = dayPassed;
    }
}
