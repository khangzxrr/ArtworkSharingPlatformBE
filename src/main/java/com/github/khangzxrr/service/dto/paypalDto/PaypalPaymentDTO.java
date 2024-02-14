package com.github.khangzxrr.service.dto.paypalDto;

import java.util.Set;

public class PaypalPaymentDTO {

    private Set<PaypalChildCaptureDTO> captures;

    public Set<PaypalChildCaptureDTO> getCaptures() {
        return captures;
    }

    public void setCaptures(Set<PaypalChildCaptureDTO> captures) {
        this.captures = captures;
    }
}
