package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A RequestProgressAttachment.
 */
@Entity
@Table(name = "request_progress_attachment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestProgressAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties(value = { "requestProgressAttachment", "requestAttachment", "artworkAsset", "certificate" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Media media;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "transaction", "attachments", "request" }, allowSetters = true)
    private RequestProgress requestProgress;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestProgressAttachment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public RequestProgressAttachment media(Media media) {
        this.setMedia(media);
        return this;
    }

    public RequestProgress getRequestProgress() {
        return this.requestProgress;
    }

    public void setRequestProgress(RequestProgress requestProgress) {
        this.requestProgress = requestProgress;
    }

    public RequestProgressAttachment requestProgress(RequestProgress requestProgress) {
        this.setRequestProgress(requestProgress);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestProgressAttachment)) {
            return false;
        }
        return getId() != null && getId().equals(((RequestProgressAttachment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestProgressAttachment{" +
            "id=" + getId() +
            "}";
    }
}
