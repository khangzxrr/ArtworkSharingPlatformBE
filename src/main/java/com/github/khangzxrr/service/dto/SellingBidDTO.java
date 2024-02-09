package com.github.khangzxrr.service.dto;

import com.github.khangzxrr.domain.enumeration.SellingBidStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.SellingBid} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SellingBidDTO implements Serializable {

    private Long id;

    private Long bidPrice;

    private LocalDate createAt;

    private SellingBidStatus status;

    private WalletTransactionDTO transaction;

    private ArtworkSellingDTO artworkSelling;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Long bidPrice) {
        this.bidPrice = bidPrice;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public SellingBidStatus getStatus() {
        return status;
    }

    public void setStatus(SellingBidStatus status) {
        this.status = status;
    }

    public WalletTransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(WalletTransactionDTO transaction) {
        this.transaction = transaction;
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
            ", createAt='" + getCreateAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", transaction=" + getTransaction() +
            ", artworkSelling=" + getArtworkSelling() +
            "}";
    }
}
