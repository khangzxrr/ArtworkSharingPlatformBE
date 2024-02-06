package com.github.khangzxrr.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.RequestBid} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestBidDTO implements Serializable {

    private Long id;

    private String description;

    private Long price;

    private Long duration;

    @Schema(accessMode = AccessMode.READ_ONLY)
    private RequestBidStatus status;

    @Schema(accessMode = AccessMode.READ_ONLY)
    private UserDTO user;

    @JsonIgnore
    private RequestDTO request;

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public RequestBidStatus getStatus() {
        return status;
    }

    public void setStatus(RequestBidStatus status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
        if (!(o instanceof RequestBidDTO)) {
            return false;
        }

        RequestBidDTO requestBidDTO = (RequestBidDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestBidDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestBidDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", duration=" + getDuration() +
            ", status='" + getStatus() + "'" +
            ", user=" + getUser() +
            ", request=" + getRequest() +
            "}";
    }
}
