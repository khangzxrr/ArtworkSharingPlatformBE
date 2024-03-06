package com.github.khangzxrr.service.dto.artworkDTOs;

import com.github.khangzxrr.domain.enumeration.ArtworkVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public class CreateArtworkDTO {

    @NotBlank
    private String name;

    @NotNull
    private long categoryId;

    @NotBlank
    private String description;

    @NotBlank
    @URL
    private String thumbnail;

    @NotNull
    private ArtworkVisibility visibility;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArtworkVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(ArtworkVisibility visibility) {
        this.visibility = visibility;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
