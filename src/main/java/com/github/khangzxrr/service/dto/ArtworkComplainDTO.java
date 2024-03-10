package com.github.khangzxrr.service.dto;

import com.github.khangzxrr.domain.enumeration.ComplainStatus;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.ArtworkComplain} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkComplainDTO implements Serializable {

    private Long id;

    private String content;

    private ComplainStatus status;

    private UserDTO user;

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

    public ComplainStatus getStatus() {
        return status;
    }

    public void setStatus(ComplainStatus status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
        if (!(o instanceof ArtworkComplainDTO)) {
            return false;
        }

        ArtworkComplainDTO artworkComplainDTO = (ArtworkComplainDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkComplainDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkComplainDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", user=" + getUser() +
            ", artwork=" + getArtwork() +
            "}";
    }
}
