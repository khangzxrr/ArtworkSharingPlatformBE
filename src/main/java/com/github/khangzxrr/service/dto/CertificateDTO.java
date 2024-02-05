package com.github.khangzxrr.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.Certificate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CertificateDTO implements Serializable {

    private Long id;

    private String description;

    private MediaDTO media;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MediaDTO getMedia() {
        return media;
    }

    public void setMedia(MediaDTO media) {
        this.media = media;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CertificateDTO)) {
            return false;
        }

        CertificateDTO certificateDTO = (CertificateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, certificateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CertificateDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", media=" + getMedia() +
            ", user=" + getUser() +
            "}";
    }
}
