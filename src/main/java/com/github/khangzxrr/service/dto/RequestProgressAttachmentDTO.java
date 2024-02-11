package com.github.khangzxrr.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.khangzxrr.domain.RequestProgressAttachment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestProgressAttachmentDTO implements Serializable {

    private Long id;

    private MediaDTO media;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestProgressAttachmentDTO)) {
            return false;
        }

        RequestProgressAttachmentDTO requestProgressAttachmentDTO = (RequestProgressAttachmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestProgressAttachmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestProgressAttachmentDTO{" +
            "id=" + getId() +
            ", media=" + getMedia() +
            "}";
    }
}
