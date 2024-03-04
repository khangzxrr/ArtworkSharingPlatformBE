package com.github.khangzxrr.web.rest.errors;

public class DayLeftMustPositiveException extends BadRequestAlertException {

    public DayLeftMustPositiveException() {
        super("Day left must be positive value", "refund", "dayLeftMustBePossitiveValue");
    }
}
