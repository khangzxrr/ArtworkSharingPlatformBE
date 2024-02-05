package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A RequestBid.
 */
@Entity
@Table(name = "request_bid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestBid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "deadline")
    private Integer deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestBidStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestBids", "requestProgresses", "attachments", "user" }, allowSetters = true)
    private Request request;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestBid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public RequestBid description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public RequestBid price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDeadline() {
        return this.deadline;
    }

    public RequestBid deadline(Integer deadline) {
        this.setDeadline(deadline);
        return this;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public RequestBidStatus getStatus() {
        return this.status;
    }

    public RequestBid status(RequestBidStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RequestBidStatus status) {
        this.status = status;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RequestBid user(User user) {
        this.setUser(user);
        return this;
    }

    public Request getRequest() {
        return this.request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public RequestBid request(Request request) {
        this.setRequest(request);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestBid)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestBid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestBid{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", deadline=" + getDeadline() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
