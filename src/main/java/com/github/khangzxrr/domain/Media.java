package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Media.
 */
@Entity
@Table(name = "media")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @JsonIgnoreProperties(value = { "media", "artwork" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "media")
    private ArtworkAsset artworkAsset;

    @JsonIgnoreProperties(value = { "media", "user" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "media")
    private Certificate certificate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Media id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public Media url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArtworkAsset getArtworkAsset() {
        return this.artworkAsset;
    }

    public void setArtworkAsset(ArtworkAsset artworkAsset) {
        if (this.artworkAsset != null) {
            this.artworkAsset.setMedia(null);
        }
        if (artworkAsset != null) {
            artworkAsset.setMedia(this);
        }
        this.artworkAsset = artworkAsset;
    }

    public Media artworkAsset(ArtworkAsset artworkAsset) {
        this.setArtworkAsset(artworkAsset);
        return this;
    }

    public Certificate getCertificate() {
        return this.certificate;
    }

    public void setCertificate(Certificate certificate) {
        if (this.certificate != null) {
            this.certificate.setMedia(null);
        }
        if (certificate != null) {
            certificate.setMedia(this);
        }
        this.certificate = certificate;
    }

    public Media certificate(Certificate certificate) {
        this.setCertificate(certificate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Media)) {
            return false;
        }
        return getId() != null && getId().equals(((Media) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Media{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
