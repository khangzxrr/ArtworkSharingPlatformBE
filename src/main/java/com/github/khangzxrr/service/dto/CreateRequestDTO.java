package com.github.khangzxrr.service.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateRequestDTO {

    @NotNull
    private String description;

    @NotNull
    private List<String> attachments;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
