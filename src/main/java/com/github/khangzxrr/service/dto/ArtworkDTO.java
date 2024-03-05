package com.github.khangzxrr.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.khangzxrr.domain.enumeration.ArtworkStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.Artwork} entity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String createAt;

    private ArtworkStatus status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArtworkSellingDTO artworkSelling;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private UserDTO owner;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArtworkCategoryDTO category;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long numberOfLikes;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<ArtworkCommentDTO> comments;

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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
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

    public Long getNumberOfLikes() {
        return this.numberOfLikes;
    }

    public void setNumberOfLikes(Long numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Set<ArtworkCommentDTO> getArtworkComment() {
        return comments;
    }

    public void setArtworkComment(Set<ArtworkCommentDTO> artworkComment) {
        this.comments = artworkComment;
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
            ", createAt='" + getCreateAt() + "'" +
            ", status='" + getStatus() + "'" +
            ", artworkSelling=" + getArtworkSelling() +
            ", owner=" + getOwner() +
            ", category=" + getCategory() +
            "}";
    }
}
