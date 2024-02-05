package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "request")
    @JsonIgnoreProperties(value = { "user", "request" }, allowSetters = true)
    private Set<RequestBid> requestBids = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "request")
    @JsonIgnoreProperties(value = { "transaction", "request" }, allowSetters = true)
    private Set<RequestProgress> requestProgresses = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Request id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Request description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestStatus getStatus() {
        return this.status;
    }

    public Request status(RequestStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Set<RequestBid> getRequestBids() {
        return this.requestBids;
    }

    public void setRequestBids(Set<RequestBid> requestBids) {
        if (this.requestBids != null) {
            this.requestBids.forEach(i -> i.setRequest(null));
        }
        if (requestBids != null) {
            requestBids.forEach(i -> i.setRequest(this));
        }
        this.requestBids = requestBids;
    }

    public Request requestBids(Set<RequestBid> requestBids) {
        this.setRequestBids(requestBids);
        return this;
    }

    public Request addRequestBids(RequestBid requestBid) {
        this.requestBids.add(requestBid);
        requestBid.setRequest(this);
        return this;
    }

    public Request removeRequestBids(RequestBid requestBid) {
        this.requestBids.remove(requestBid);
        requestBid.setRequest(null);
        return this;
    }

    public Set<RequestProgress> getRequestProgresses() {
        return this.requestProgresses;
    }

    public void setRequestProgresses(Set<RequestProgress> requestProgresses) {
        if (this.requestProgresses != null) {
            this.requestProgresses.forEach(i -> i.setRequest(null));
        }
        if (requestProgresses != null) {
            requestProgresses.forEach(i -> i.setRequest(this));
        }
        this.requestProgresses = requestProgresses;
    }

    public Request requestProgresses(Set<RequestProgress> requestProgresses) {
        this.setRequestProgresses(requestProgresses);
        return this;
    }

    public Request addRequestProgresses(RequestProgress requestProgress) {
        this.requestProgresses.add(requestProgress);
        requestProgress.setRequest(this);
        return this;
    }

    public Request removeRequestProgresses(RequestProgress requestProgress) {
        this.requestProgresses.remove(requestProgress);
        requestProgress.setRequest(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Request user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return getId() != null && getId().equals(((Request) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
