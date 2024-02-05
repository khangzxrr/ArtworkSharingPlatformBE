package com.github.khangzxrr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.khangzxrr.domain.enumeration.ArtworkStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Artwork.
 */
@Entity
@Table(name = "artwork")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Artwork implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_at")
    private String createAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArtworkStatus status;

    @JsonIgnoreProperties(value = { "bids", "artwork" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private ArtworkSelling artworkSelling;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artwork")
    @JsonIgnoreProperties(value = { "media", "artwork" }, allowSetters = true)
    private Set<ArtworkAsset> artworkAssets = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artwork")
    @JsonIgnoreProperties(value = { "owner", "artwork" }, allowSetters = true)
    private Set<ArtworkComment> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artwork")
    @JsonIgnoreProperties(value = { "user", "artwork" }, allowSetters = true)
    private Set<ArtworkComplain> complains = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "artwork")
    @JsonIgnoreProperties(value = { "owner", "artwork" }, allowSetters = true)
    private Set<ArtworkLike> likes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "artworks" }, allowSetters = true)
    private ArtworkCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Artwork id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Artwork name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Artwork description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateAt() {
        return this.createAt;
    }

    public Artwork createAt(String createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public ArtworkStatus getStatus() {
        return this.status;
    }

    public Artwork status(ArtworkStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ArtworkStatus status) {
        this.status = status;
    }

    public ArtworkSelling getArtworkSelling() {
        return this.artworkSelling;
    }

    public void setArtworkSelling(ArtworkSelling artworkSelling) {
        this.artworkSelling = artworkSelling;
    }

    public Artwork artworkSelling(ArtworkSelling artworkSelling) {
        this.setArtworkSelling(artworkSelling);
        return this;
    }

    public Set<ArtworkAsset> getArtworkAssets() {
        return this.artworkAssets;
    }

    public void setArtworkAssets(Set<ArtworkAsset> artworkAssets) {
        if (this.artworkAssets != null) {
            this.artworkAssets.forEach(i -> i.setArtwork(null));
        }
        if (artworkAssets != null) {
            artworkAssets.forEach(i -> i.setArtwork(this));
        }
        this.artworkAssets = artworkAssets;
    }

    public Artwork artworkAssets(Set<ArtworkAsset> artworkAssets) {
        this.setArtworkAssets(artworkAssets);
        return this;
    }

    public Artwork addArtworkAssets(ArtworkAsset artworkAsset) {
        this.artworkAssets.add(artworkAsset);
        artworkAsset.setArtwork(this);
        return this;
    }

    public Artwork removeArtworkAssets(ArtworkAsset artworkAsset) {
        this.artworkAssets.remove(artworkAsset);
        artworkAsset.setArtwork(null);
        return this;
    }

    public Set<ArtworkComment> getComments() {
        return this.comments;
    }

    public void setComments(Set<ArtworkComment> artworkComments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setArtwork(null));
        }
        if (artworkComments != null) {
            artworkComments.forEach(i -> i.setArtwork(this));
        }
        this.comments = artworkComments;
    }

    public Artwork comments(Set<ArtworkComment> artworkComments) {
        this.setComments(artworkComments);
        return this;
    }

    public Artwork addComments(ArtworkComment artworkComment) {
        this.comments.add(artworkComment);
        artworkComment.setArtwork(this);
        return this;
    }

    public Artwork removeComments(ArtworkComment artworkComment) {
        this.comments.remove(artworkComment);
        artworkComment.setArtwork(null);
        return this;
    }

    public Set<ArtworkComplain> getComplains() {
        return this.complains;
    }

    public void setComplains(Set<ArtworkComplain> artworkComplains) {
        if (this.complains != null) {
            this.complains.forEach(i -> i.setArtwork(null));
        }
        if (artworkComplains != null) {
            artworkComplains.forEach(i -> i.setArtwork(this));
        }
        this.complains = artworkComplains;
    }

    public Artwork complains(Set<ArtworkComplain> artworkComplains) {
        this.setComplains(artworkComplains);
        return this;
    }

    public Artwork addComplains(ArtworkComplain artworkComplain) {
        this.complains.add(artworkComplain);
        artworkComplain.setArtwork(this);
        return this;
    }

    public Artwork removeComplains(ArtworkComplain artworkComplain) {
        this.complains.remove(artworkComplain);
        artworkComplain.setArtwork(null);
        return this;
    }

    public Set<ArtworkLike> getLikes() {
        return this.likes;
    }

    public void setLikes(Set<ArtworkLike> artworkLikes) {
        if (this.likes != null) {
            this.likes.forEach(i -> i.setArtwork(null));
        }
        if (artworkLikes != null) {
            artworkLikes.forEach(i -> i.setArtwork(this));
        }
        this.likes = artworkLikes;
    }

    public Artwork likes(Set<ArtworkLike> artworkLikes) {
        this.setLikes(artworkLikes);
        return this;
    }

    public Artwork addLikes(ArtworkLike artworkLike) {
        this.likes.add(artworkLike);
        artworkLike.setArtwork(this);
        return this;
    }

    public Artwork removeLikes(ArtworkLike artworkLike) {
        this.likes.remove(artworkLike);
        artworkLike.setArtwork(null);
        return this;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Artwork owner(User user) {
        this.setOwner(user);
        return this;
    }

    public ArtworkCategory getCategory() {
        return this.category;
    }

    public void setCategory(ArtworkCategory artworkCategory) {
        this.category = artworkCategory;
    }

    public Artwork category(ArtworkCategory artworkCategory) {
        this.setCategory(artworkCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artwork)) {
            return false;
        }
        return getId() != null && getId().equals(((Artwork) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artwork{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createAt='" + getCreateAt() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
