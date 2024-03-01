package com.github.khangzxrr.service.dto.requestChatDTOs;

import com.github.khangzxrr.service.dto.UserDTO;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

public class RequestChatDTO implements Serializable {

    private Long id;

    private UserDTO fromUser;

    @NotBlank
    private String message;

    private Instant createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDTO fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
