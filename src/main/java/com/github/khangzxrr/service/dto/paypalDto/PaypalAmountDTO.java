package com.github.khangzxrr.service.dto.paypalDto;

public class PaypalAmountDTO {

    private String currency_code;
    private String value;

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
