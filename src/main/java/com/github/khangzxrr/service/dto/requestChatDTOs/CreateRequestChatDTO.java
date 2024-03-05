package com.github.khangzxrr.service.dto.requestChatDTOs;

import jakarta.validation.constraints.NotBlank;

public class CreateRequestChatDTO {

    @NotBlank
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
