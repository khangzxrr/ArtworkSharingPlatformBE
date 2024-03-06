package com.github.khangzxrr.service.dto;

import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.ArtworkLike} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkLikeDTO implements Serializable {

    private Long id;

    private LocalDate createAt;

    private UserDTO owner;

    private ArtworkDTO artwork;

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

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public ArtworkDTO getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkDTO artwork) {
        this.artwork = artwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkLikeDTO)) {
            return false;
        }

        ArtworkLikeDTO artworkLikeDTO = (ArtworkLikeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkLikeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkLikeDTO{" +
            "id=" + getId() +
            ", createAt='" + getCreateAt() + "'" +
            ", owner=" + getOwner() +
            ", artwork=" + getArtwork() +
            "}";
    }
}
