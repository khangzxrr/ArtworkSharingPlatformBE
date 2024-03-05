package com.github.khangzxrr.service.dto.paypalDto;

public class PaypalChildCaptureDTO {

    private String id;
    private String status;
    private PaypalAmountDTO amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaypalAmountDTO getAmount() {
        return amount;
    }

    public void setAmount(PaypalAmountDTO amount) {
        this.amount = amount;
    }
}
