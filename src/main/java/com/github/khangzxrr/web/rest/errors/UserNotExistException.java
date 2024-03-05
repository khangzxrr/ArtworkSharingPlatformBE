package com.github.khangzxrr.web.rest.errors;

public class UserNotExistException extends BadRequestAlertException {

    public UserNotExistException() {
        super("User does not exist", "user", "userNotExist");
    }
}
