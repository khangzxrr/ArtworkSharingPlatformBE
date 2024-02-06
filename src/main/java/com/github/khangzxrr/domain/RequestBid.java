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
    private Long price;

    @Column(name = "duration")
    private Long duration;

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

    public Long getPrice() {
        return this.price;
    }

    public RequestBid price(Long price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDuration() {
        return this.duration;
    }

    public RequestBid duration(Long duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
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
            ", duration=" + getDuration() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
