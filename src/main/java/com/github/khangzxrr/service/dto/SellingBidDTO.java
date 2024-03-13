package com.github.khangzxrr.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.khangzxrr.domain.enumeration.SellingBidStatus;
import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.Min;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.SellingBid} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SellingBidDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Double bidPrice;

    private Instant createdDate;

    private SellingBidStatus status;

    private UserDTO bidder;

    public UserDTO getBidder() {
        return bidder;
    }

    public void setBidder(UserDTO bidder) {
        this.bidder = bidder;
    }

    @JsonIgnore
    private ArtworkSellingDTO artworkSelling;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public SellingBidStatus getStatus() {
        return status;
    }

    public void setStatus(SellingBidStatus status) {
        this.status = status;
    }

    public ArtworkSellingDTO getArtworkSelling() {
        return artworkSelling;
    }

    public void setArtworkSelling(ArtworkSellingDTO artworkSelling) {
        this.artworkSelling = artworkSelling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SellingBidDTO)) {
            return false;
        }

        SellingBidDTO sellingBidDTO = (SellingBidDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sellingBidDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SellingBidDTO{" +
            "id=" + getId() +
            ", bidPrice=" + getBidPrice() +
            ", createAt='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", artworkSelling=" + getArtworkSelling() +
            "}";
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
