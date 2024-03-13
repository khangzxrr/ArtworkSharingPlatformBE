package com.github.khangzxrr.service.dto.artworkDTOs;

import com.github.khangzxrr.domain.enumeration.ArtworkStatus;
import com.github.khangzxrr.domain.enumeration.ArtworkVisibility;
import com.github.khangzxrr.service.dto.ArtworkAssetDTO;
import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import java.io.Serializable;
import java.util.List;
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

    private List<ArtworkSellingDTO> artworkSellings;

    private UserDTO owner;

    private ArtworkCategoryDTO category;

    private List<ArtworkAssetDTO> artworkAssets;

    private long likesCount;

    private long commentsCount;

    private boolean userLikedThisArtwork;

    private ArtworkSellingDTO onGoingArtworkSelling;

    public ArtworkSellingDTO getOnGoingArtworkSelling() {
        return onGoingArtworkSelling;
    }

    public void setOnGoingArtworkSelling(ArtworkSellingDTO onGoingArtworkSelling) {
        this.onGoingArtworkSelling = onGoingArtworkSelling;
    }

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
            ", artworkSelling=" + getArtworkSellings() +
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

    public List<ArtworkAssetDTO> getArtworkAssets() {
        return artworkAssets;
    }

    public void setArtworkAssets(List<ArtworkAssetDTO> artworkAssets) {
        this.artworkAssets = artworkAssets;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean isUserLikedThisArtwork() {
        return userLikedThisArtwork;
    }

    public void setUserLikedThisArtwork(boolean userLikedThisArtwork) {
        this.userLikedThisArtwork = userLikedThisArtwork;
    }

    public List<ArtworkSellingDTO> getArtworkSellings() {
        return artworkSellings;
    }

    public void setArtworkSellings(List<ArtworkSellingDTO> artworkSellings) {
        this.artworkSellings = artworkSellings;
    }
}
