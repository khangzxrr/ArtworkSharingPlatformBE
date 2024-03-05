package com.github.khangzxrr.service.dto;

import com.github.khangzxrr.service.dto.paypalDto.PaypalPurchaseUnitDTO;
import java.io.Serializable;
import java.util.Set;

public class PaypalCaptureDTO implements Serializable {

    private String id;
    private String status;

    private Set<PaypalPurchaseUnitDTO> purchase_units;

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

    public Set<PaypalPurchaseUnitDTO> getPurchase_units() {
        return purchase_units;
    }

    public void setPurchase_units(Set<PaypalPurchaseUnitDTO> purchase_units) {
        this.purchase_units = purchase_units;
    }
}
