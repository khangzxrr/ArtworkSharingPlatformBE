package com.github.khangzxrr.service.dto;

import com.github.khangzxrr.domain.enumeration.ArtworkSellingStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.ArtworkSelling} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkSellingDTO implements Serializable {

    private Long id;

    private Instant createdDate;

    @NotNull
    private ArtworkSellingType type;

    private ArtworkSellingStatus status;

    @NotNull
    @Min(1)
    private Long sellingDuration;

    @NotNull
    @Min(0)
    private Double expectedSellingPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArtworkSellingType getType() {
        return type;
    }

    public void setType(ArtworkSellingType type) {
        this.type = type;
    }

    public ArtworkSellingStatus getStatus() {
        return status;
    }

    public void setStatus(ArtworkSellingStatus status) {
        this.status = status;
    }

    public Double getExpectedSellingPrice() {
        return expectedSellingPrice;
    }

    public void setExpectedSellingPrice(Double expectedSellingPrice) {
        this.expectedSellingPrice = expectedSellingPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkSellingDTO)) {
            return false;
        }

        ArtworkSellingDTO artworkSellingDTO = (ArtworkSellingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkSellingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkSellingDTO{" +
            "id=" + getId() +
            ", createAt='" + getCreatedDate() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", expectedSellingPrice=" + getExpectedSellingPrice() +
            "}";
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getSellingDuration() {
        return sellingDuration;
    }

    public void setSellingDuration(Long sellingDuration) {
        this.sellingDuration = sellingDuration;
    }
}
