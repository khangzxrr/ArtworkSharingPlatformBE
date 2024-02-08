package com.github.khangzxrr.service.dto.requestProgressDto;

import com.github.khangzxrr.domain.enumeration.RequestProgressStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressType;
import java.io.Serializable;

public class RequestProgressPaymentDTO implements Serializable {

    private long amount;
    private RequestProgressType type;
    private RequestProgressStatus status;

    public RequestProgressPaymentDTO(long amount, RequestProgressType type, RequestProgressStatus status) {
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public RequestProgressType getType() {
        return type;
    }

    public void setType(RequestProgressType type) {
        this.type = type;
    }

    public RequestProgressStatus getStatus() {
        return status;
    }

    public void setStatus(RequestProgressStatus status) {
        this.status = status;
    }
}
