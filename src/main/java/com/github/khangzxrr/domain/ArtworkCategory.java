package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ArtworkCategory.
 */
@Entity
@Table(name = "artwork_category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @JsonIgnoreProperties(
        value = { "artworkSelling", "artworkAssets", "comments", "complains", "likes", "owner", "category" },
        allowSetters = true
    )
    private Set<Artwork> artworks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArtworkCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ArtworkCategory name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Artwork> getArtworks() {
        return this.artworks;
    }

    public void setArtworks(Set<Artwork> artworks) {
        if (this.artworks != null) {
            this.artworks.forEach(i -> i.setCategory(null));
        }
        if (artworks != null) {
            artworks.forEach(i -> i.setCategory(this));
        }
        this.artworks = artworks;
    }

    public ArtworkCategory artworks(Set<Artwork> artworks) {
        this.setArtworks(artworks);
        return this;
    }

    public ArtworkCategory addArtworks(Artwork artwork) {
        this.artworks.add(artwork);
        artwork.setCategory(this);
        return this;
    }

    public ArtworkCategory removeArtworks(Artwork artwork) {
        this.artworks.remove(artwork);
        artwork.setCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkCategory)) {
            return false;
        }
        return getId() != null && getId().equals(((ArtworkCategory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
