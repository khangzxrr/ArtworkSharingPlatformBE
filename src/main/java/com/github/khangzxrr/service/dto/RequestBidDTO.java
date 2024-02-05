package com.github.khangzxrr.service.dto;

import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.RequestBid} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestBidDTO implements Serializable {

    private Long id;

    private String description;

    private Double price;

    private Integer deadline;

    private RequestBidStatus status;

    private UserDTO user;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
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
            ", deadline=" + getDeadline() +
            ", status='" + getStatus() + "'" +
            ", user=" + getUser() +
            ", request=" + getRequest() +
            "}";
    }
}
