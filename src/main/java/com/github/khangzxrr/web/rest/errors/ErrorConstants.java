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
    public static final URI REQUEST_IS_NOT_ON_CORRECT_STATE = URI.create(PROBLEM_BASE_URL + "/request-is-not-on-correct-state");
    public static final URI REQUEST_IS_BELONG_TO_CURRENT_USER = URI.create(PROBLEM_BASE_URL + "/request-is-not-belong-to-current-user");

    private ErrorConstants() {}
}
