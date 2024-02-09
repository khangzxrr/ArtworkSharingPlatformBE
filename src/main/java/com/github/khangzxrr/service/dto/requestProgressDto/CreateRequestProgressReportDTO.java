package com.github.khangzxrr.service.dto.requestProgressDto;

import com.github.khangzxrr.domain.enumeration.RequestProgressType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import org.hibernate.validator.constraints.URL;

public class CreateRequestProgressReportDTO implements Serializable {

    @NotNull
    private String description;

    @NotNull
    private RequestProgressType type;

    @NotNull
    private List<@URL String> attachments;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestProgressType getType() {
        return type;
    }

    public void setType(RequestProgressType type) {
        this.type = type;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
