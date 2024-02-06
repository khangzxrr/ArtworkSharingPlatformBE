package com.github.khangzxrr.service.dto;

import com.github.khangzxrr.domain.enumeration.RequestStatus;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.Request} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestDTO implements Serializable {

    private Long id;

    private String description;

    private RequestStatus status;

    private UserDTO user;

    private List<RequestAttachmentDTO> attachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDTO)) {
            return false;
        }

        RequestDTO requestDTO = (RequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", user=" + getUser() +
            "}";
    }

    public List<RequestAttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<RequestAttachmentDTO> attachments) {
        this.attachments = attachments;
    }
}
