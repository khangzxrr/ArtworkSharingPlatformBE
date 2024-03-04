package com.github.khangzxrr.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.khangzxrr.domain.enumeration.RequestProgressStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressType;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.RequestProgress} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestProgressDTO implements Serializable {

    private Long id;

    private Instant createdDate;

    private String description;

    private RequestProgressType type;

    private RequestProgressStatus status;

    private WalletTransactionDTO transaction;

    @JsonIgnore
    private RequestDTO request;

    private List<RequestProgressAttachmentDTO> attachments;

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

    public RequestProgressType getType() {
        return type;
    }

    public void setType(RequestProgressType type) {
        this.type = type;
    }

    public RequestProgressStatus getStatus() {
        return status;
    }

    public void setStatus(RequestProgressStatus status) {
        this.status = status;
    }

    public WalletTransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(WalletTransactionDTO transaction) {
        this.transaction = transaction;
    }

    public RequestDTO getRequest() {
        return request;
    }

    public void setRequest(RequestDTO request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestProgressDTO)) {
            return false;
        }

        RequestProgressDTO requestProgressDTO = (RequestProgressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestProgressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestProgressDTO{" +
            "id=" + getId() +
            ", date='" + getCreatedDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", transaction=" + getTransaction() +
            ", request=" + getRequest() +
            "}";
    }

    public List<RequestProgressAttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<RequestProgressAttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
