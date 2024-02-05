package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.khangzxrr.domain.enumeration.SellingBidStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SellingBid.
 */
@Entity
@Table(name = "selling_bid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SellingBid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bid_price")
    private Long bidPrice;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SellingBidStatus status;

    @JsonIgnoreProperties(value = { "wallet", "requestProgress", "sellingBid" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private WalletTransaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bids", "artwork" }, allowSetters = true)
    private ArtworkSelling artworkSelling;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SellingBid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBidPrice() {
        return this.bidPrice;
    }

    public SellingBid bidPrice(Long bidPrice) {
        this.setBidPrice(bidPrice);
        return this;
    }

    public void setBidPrice(Long bidPrice) {
        this.bidPrice = bidPrice;
    }

    public LocalDate getCreateAt() {
        return this.createAt;
    }

    public SellingBid createAt(LocalDate createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public SellingBidStatus getStatus() {
        return this.status;
    }

    public SellingBid status(SellingBidStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(SellingBidStatus status) {
        this.status = status;
    }

    public WalletTransaction getTransaction() {
        return this.transaction;
    }

    public void setTransaction(WalletTransaction walletTransaction) {
        this.transaction = walletTransaction;
    }

    public SellingBid transaction(WalletTransaction walletTransaction) {
        this.setTransaction(walletTransaction);
        return this;
    }

    public ArtworkSelling getArtworkSelling() {
        return this.artworkSelling;
    }

    public void setArtworkSelling(ArtworkSelling artworkSelling) {
        this.artworkSelling = artworkSelling;
    }

    public SellingBid artworkSelling(ArtworkSelling artworkSelling) {
        this.setArtworkSelling(artworkSelling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SellingBid)) {
            return false;
        }
        return getId() != null && getId().equals(((SellingBid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SellingBid{" +
            "id=" + getId() +
            ", bidPrice=" + getBidPrice() +
            ", createAt='" + getCreateAt() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
