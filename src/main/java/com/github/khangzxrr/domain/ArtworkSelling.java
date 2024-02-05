package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A ArtworkSelling.
 */
@Entity
@Table(name = "artwork_selling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkSelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ArtworkSellingType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArtworkSellingStatus status;

    @Column(name = "expected_selling_price")
    private Long expectedSellingPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artworkSelling")
    @JsonIgnoreProperties(value = { "transaction", "artworkSelling" }, allowSetters = true)
    private Set<SellingBid> bids = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "artworkSelling", "artworkAssets", "comments", "complains", "likes", "owner", "category" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "artworkSelling")
    private Artwork artwork;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArtworkSelling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateAt() {
        return this.createAt;
    }

    public ArtworkSelling createAt(LocalDate createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public ArtworkSellingType getType() {
        return this.type;
    }

    public ArtworkSelling type(ArtworkSellingType type) {
        this.setType(type);
        return this;
    }

    public void setType(ArtworkSellingType type) {
        this.type = type;
    }

    public ArtworkSellingStatus getStatus() {
        return this.status;
    }

    public ArtworkSelling status(ArtworkSellingStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ArtworkSellingStatus status) {
        this.status = status;
    }

    public Long getExpectedSellingPrice() {
        return this.expectedSellingPrice;
    }

    public ArtworkSelling expectedSellingPrice(Long expectedSellingPrice) {
        this.setExpectedSellingPrice(expectedSellingPrice);
        return this;
    }

    public void setExpectedSellingPrice(Long expectedSellingPrice) {
        this.expectedSellingPrice = expectedSellingPrice;
    }

    public Set<SellingBid> getBids() {
        return this.bids;
    }

    public void setBids(Set<SellingBid> sellingBids) {
        if (this.bids != null) {
            this.bids.forEach(i -> i.setArtworkSelling(null));
        }
        if (sellingBids != null) {
            sellingBids.forEach(i -> i.setArtworkSelling(this));
        }
        this.bids = sellingBids;
    }

    public ArtworkSelling bids(Set<SellingBid> sellingBids) {
        this.setBids(sellingBids);
        return this;
    }

    public ArtworkSelling addBids(SellingBid sellingBid) {
        this.bids.add(sellingBid);
        sellingBid.setArtworkSelling(this);
        return this;
    }

    public ArtworkSelling removeBids(SellingBid sellingBid) {
        this.bids.remove(sellingBid);
        sellingBid.setArtworkSelling(null);
        return this;
    }

    public Artwork getArtwork() {
        return this.artwork;
    }

    public void setArtwork(Artwork artwork) {
        if (this.artwork != null) {
            this.artwork.setArtworkSelling(null);
        }
        if (artwork != null) {
            artwork.setArtworkSelling(this);
        }
        this.artwork = artwork;
    }

    public ArtworkSelling artwork(Artwork artwork) {
        this.setArtwork(artwork);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkSelling)) {
            return false;
        }
        return getId() != null && getId().equals(((ArtworkSelling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkSelling{" +
            "id=" + getId() +
            ", createAt='" + getCreateAt() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", expectedSellingPrice=" + getExpectedSellingPrice() +
            "}";
    }
}
