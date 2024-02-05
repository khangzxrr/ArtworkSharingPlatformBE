package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.khangzxrr.domain.enumeration.ComplainStatus;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A ArtworkComplain.
 */
@Entity
@Table(name = "artwork_complain")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkComplain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ComplainStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "artworkSelling", "artworkAssets", "comments", "complains", "likes", "owner", "category" },
        allowSetters = true
    )
    private Artwork artwork;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArtworkComplain id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public ArtworkComplain content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ComplainStatus getStatus() {
        return this.status;
    }

    public ArtworkComplain status(ComplainStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ComplainStatus status) {
        this.status = status;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArtworkComplain user(User user) {
        this.setUser(user);
        return this;
    }

    public Artwork getArtwork() {
        return this.artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public ArtworkComplain artwork(Artwork artwork) {
        this.setArtwork(artwork);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkComplain)) {
            return false;
        }
        return getId() != null && getId().equals(((ArtworkComplain) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkComplain{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
