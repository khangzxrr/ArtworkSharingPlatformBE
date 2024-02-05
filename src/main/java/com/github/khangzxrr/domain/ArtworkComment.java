package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ArtworkComment.
 */
@Entity
@Table(name = "artwork_comment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private LocalDate createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

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

    public ArtworkComment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public ArtworkComment content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreateAt() {
        return this.createAt;
    }

    public ArtworkComment createAt(LocalDate createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public ArtworkComment owner(User user) {
        this.setOwner(user);
        return this;
    }

    public Artwork getArtwork() {
        return this.artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public ArtworkComment artwork(Artwork artwork) {
        this.setArtwork(artwork);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkComment)) {
            return false;
        }
        return getId() != null && getId().equals(((ArtworkComment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkComment{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            "}";
    }
}
