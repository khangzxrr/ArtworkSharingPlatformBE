package com.github.khangzxrr.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI NOT_LOGGED_TYPE = URI.create(PROBLEM_BASE_URL + "/not-logged");
    public static final URI REQUEST_NOT_BELONG_TO_AUDIENCE = URI.create(PROBLEM_BASE_URL + "/quest-not-belong-to-audience");
    public static final URI REQUEST_NOT_FOUND = URI.create(PROBLEM_BASE_URL + "/request-not-found");
    public static final URI REQUEST_IS_NOT_IN_CORRECT_STATE = URI.create(PROBLEM_BASE_URL + "/request-is-not-in-correct-state");
    public static final URI REQUEST_IS_BELONG_TO_CURRENT_USER = URI.create(PROBLEM_BASE_URL + "/request-is-not-belong-to-current-user");
    public static final URI REQUEST_BID_IS_NOT_FOUND = URI.create(PROBLEM_BASE_URL + "/request-bid-is-not-found");
    public static final URI REQUEST_BID_IS_NOT_IN_CORRECT_STATE = URI.create(PROBLEM_BASE_URL + "/request-bid-is-not-in-correct-state");
    public static final URI REQUEST_PROGRESS_TYPE_IS_NOT_VALID = URI.create(PROBLEM_BASE_URL + "/request-progress-type-is-not-valid");
    public static final URI REQUEST_PAYMENT_IS_ALREADY_SUCCESSED = URI.create(PROBLEM_BASE_URL + "/request-payment-is-already-successed");
    public static final URI WALLET_AMOUNT_IS_NOT_ENOUGH = URI.create(PROBLEM_BASE_URL + "/wallet-amount-is-not-enough");
    public static final URI REQUEST_IS_OWNED_BY_USER = URI.create(PROBLEM_BASE_URL + "/request-is-owned-by-user");
    public static final URI CREATOR_IS_NOT_SELECTED_IN_REQUEST = URI.create(PROBLEM_BASE_URL + "/creator-is-not-selected-in-request");
    public static final URI REQUEST_PROGRESS_TYPE_NOT_A_REPORT = URI.create(PROBLEM_BASE_URL + "/request-progress-type-is-not-a-report");

    private ErrorConstants() {}
}
