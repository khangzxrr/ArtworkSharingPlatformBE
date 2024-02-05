package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.khangzxrr.domain.enumeration.RequestProgressStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A RequestProgress.
 */
@Entity
@Table(name = "request_progress")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RequestProgressType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestProgressStatus status;

    @JsonIgnoreProperties(value = { "wallet", "requestProgress", "sellingBid" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private WalletTransaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestBids", "requestProgresses", "user" }, allowSetters = true)
    private Request request;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestProgress id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public RequestProgress date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public RequestProgress description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestProgressType getType() {
        return this.type;
    }

    public RequestProgress type(RequestProgressType type) {
        this.setType(type);
        return this;
    }

    public void setType(RequestProgressType type) {
        this.type = type;
    }

    public RequestProgressStatus getStatus() {
        return this.status;
    }

    public RequestProgress status(RequestProgressStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RequestProgressStatus status) {
        this.status = status;
    }

    public WalletTransaction getTransaction() {
        return this.transaction;
    }

    public void setTransaction(WalletTransaction walletTransaction) {
        this.transaction = walletTransaction;
    }

    public RequestProgress transaction(WalletTransaction walletTransaction) {
        this.setTransaction(walletTransaction);
        return this;
    }

    public Request getRequest() {
        return this.request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public RequestProgress request(Request request) {
        this.setRequest(request);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestProgress)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestProgress) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestProgress{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
