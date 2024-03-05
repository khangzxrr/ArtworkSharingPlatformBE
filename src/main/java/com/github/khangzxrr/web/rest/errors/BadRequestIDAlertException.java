package com.github.khangzxrr.web.rest.errors;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import tech.jhipster.web.rest.errors.ProblemDetailWithCause;
import tech.jhipster.web.rest.errors.ProblemDetailWithCause.ProblemDetailWithCauseBuilder;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class BadRequestIDAlertException extends ErrorResponseException {

    private static final long serialVersionUID = 1L;

    private final String errorKey;
    private final Long errorId;

    public BadRequestIDAlertException(String defaultMessage, Long errorId, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, errorId, errorKey);
    }

    public BadRequestIDAlertException(URI type, String defaultMessage, Long errorId, String errorKey) {
        super(
            HttpStatus.BAD_REQUEST,
            ProblemDetailWithCauseBuilder
                .instance()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withType(type)
                .withTitle(defaultMessage)
                .withProperty("message", "error." + errorKey)
                .withProperty("errorId", errorId)
                .build(),
            null
        );
        this.errorKey = errorKey;
        this.errorId = errorId;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public Long getErrorId() {
        return errorId;
    }

    public ProblemDetailWithCause getProblemDetailWithCause() {
        return (ProblemDetailWithCause) this.getBody();
    }
}
