package com.github.khangzxrr.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.ArtworkAsset} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkAssetDTO implements Serializable {

    private Long id;

    private MediaDTO media;

    private ArtworkDTO artwork;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaDTO getMedia() {
        return media;
    }

    public void setMedia(MediaDTO media) {
        this.media = media;
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
        if (!(o instanceof ArtworkAssetDTO)) {
            return false;
        }

        ArtworkAssetDTO artworkAssetDTO = (ArtworkAssetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkAssetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkAssetDTO{" +
            "id=" + getId() +
            ", media=" + getMedia() +
            ", artwork=" + getArtwork() +
            "}";
    }
}
