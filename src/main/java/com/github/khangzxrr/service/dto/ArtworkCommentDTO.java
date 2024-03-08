package com.github.khangzxrr.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.ArtworkComment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkCommentDTO implements Serializable {

    private Long id;

    private String content;

    private Instant createdDate;

    private UserDTO owner;

    @JsonIgnore
    private ArtworkDTO artwork;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (!(o instanceof ArtworkCommentDTO)) {
            return false;
        }

        ArtworkCommentDTO artworkCommentDTO = (ArtworkCommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkCommentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkCommentDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", createAt='" + getCreatedDate() + "'" +
            ", owner=" + getOwner() +
            ", artwork=" + getArtwork() +
            "}";
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
