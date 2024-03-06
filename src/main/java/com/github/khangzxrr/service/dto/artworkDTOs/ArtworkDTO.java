package com.github.khangzxrr.service.dto.artworkDTOs;

import com.github.khangzxrr.domain.enumeration.ArtworkStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkVisibility;
import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.Artwork} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    public ArtworkVisibility visibility;

    private String createdDate;

    private ArtworkStatus status;

    private ArtworkSellingDTO artworkSelling;

    private UserDTO owner;

    private ArtworkCategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createAt) {
        this.createdDate = createAt;
    }

    public ArtworkStatus getStatus() {
        return status;
    }

    public void setStatus(ArtworkStatus status) {
        this.status = status;
    }

    public ArtworkSellingDTO getArtworkSelling() {
        return artworkSelling;
    }

    public void setArtworkSelling(ArtworkSellingDTO artworkSelling) {
        this.artworkSelling = artworkSelling;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public ArtworkCategoryDTO getCategory() {
        return category;
    }

    public void setCategory(ArtworkCategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkDTO)) {
            return false;
        }

        ArtworkDTO artworkDTO = (ArtworkDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createAt='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", artworkSelling=" + getArtworkSelling() +
            ", owner=" + getOwner() +
            ", category=" + getCategory() +
            "}";
    }

    public ArtworkVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(ArtworkVisibility visibility) {
        this.visibility = visibility;
    }
}
