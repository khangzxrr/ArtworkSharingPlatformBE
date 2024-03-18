package com.github.khangzxrr.service.dto.artworkDTOs;

import com.github.khangzxrr.domain.enumeration.ArtworkVisibility;
import com.github.khangzxrr.service.dto.ArtworkAssetDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateArtworkDTO {

    @NotBlank
    private String name;

    @NotNull
    private long categoryId;

    @NotBlank
    private String description;

    @NotNull
    private List<ArtworkAssetDTO> assets;

    @NotNull
    private ArtworkVisibility visibility;

    @NotNull
    private boolean isPhysicalArtwork;

    @NotNull
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPhysicalArtwork() {
        return isPhysicalArtwork;
    }

    public void setPhysicalArtwork(boolean physicalArtwork) {
        isPhysicalArtwork = physicalArtwork;
    }

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

    public List<ArtworkAssetDTO> getAssets() {
        return assets;
    }

    public void setAssets(List<ArtworkAssetDTO> assets) {
        this.assets = assets;
    }
}
