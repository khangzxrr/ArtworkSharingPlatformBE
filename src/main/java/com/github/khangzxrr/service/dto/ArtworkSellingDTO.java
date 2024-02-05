package com.github.khangzxrr.service.dto;

import com.github.khangzxrr.domain.enumeration.ArtworkSellingStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkSellingType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.ArtworkSelling} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkSellingDTO implements Serializable {

    private Long id;

    private LocalDate createAt;

    private ArtworkSellingType type;

    private ArtworkSellingStatus status;

    private Long expectedSellingPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
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

    public Long getExpectedSellingPrice() {
        return expectedSellingPrice;
    }

    public void setExpectedSellingPrice(Long expectedSellingPrice) {
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
            ", createAt='" + getCreateAt() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", expectedSellingPrice=" + getExpectedSellingPrice() +
            "}";
    }
}
