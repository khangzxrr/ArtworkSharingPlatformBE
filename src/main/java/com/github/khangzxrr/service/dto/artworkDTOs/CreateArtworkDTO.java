package com.github.khangzxrr.service.dto.artworkDTOs;

import jakarta.validation.constraints.NotBlank;

public class CreateArtworkDTO {

    @NotBlank
    private String description;
}
