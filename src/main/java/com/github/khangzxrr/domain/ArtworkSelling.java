package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingType;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A ArtworkSelling.
 */
@Entity
@Table(name = "artwork_selling")
public class ArtworkSelling extends AbstractAuditingEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ArtworkSellingType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArtworkSellingStatus status;

    @Column(name = "selling_duration")
    private Long sellingDuration;

    @Column(name = "expected_selling_price")
    private Double expectedSellingPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artworkSelling", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "transaction", "artworkSelling" }, allowSetters = true)
    private Set<SellingBid> bids = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "artworkSelling", "artworkAssets", "comments", "complains", "likes", "owner", "category" },
        allowSetters = true
    )
    @ManyToOne(fetch = FetchType.LAZY)
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
            ", createAt='" + getCreatedDate() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", sellingDuration='" + getSellingDuration() + "'" +
            "}";
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getSellingDuration() {
        return sellingDuration;
    }

    public void setSellingDuration(Long sellingDuration) {
        this.sellingDuration = sellingDuration;
    }

    public Double getExpectedSellingPrice() {
        return expectedSellingPrice;
    }

    public void setExpectedSellingPrice(Double expectedSellingPrice) {
        this.expectedSellingPrice = expectedSellingPrice;
    }
}
