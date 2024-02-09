package com.github.khangzxrr.web.rest.errors;

public class CreatorIsNotSelectedInRequest extends BadRequestAlertException {

    public CreatorIsNotSelectedInRequest() {
        super(
            ErrorConstants.CREATOR_IS_NOT_SELECTED_IN_REQUEST,
            "Creator is not selected in request",
            "request",
            "creatorIsNotSelectedInRequest"
        );
    }
}
