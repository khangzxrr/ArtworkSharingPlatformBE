package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkCategory;
import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.CreateArtworkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artwork} and its DTO {@link ArtworkDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkMapper extends EntityMapper<ArtworkDTO, Artwork> {
    Artwork toEntity(CreateArtworkDTO s);

    @Mapping(target = "artworkSelling", source = "artworkSelling")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "category", source = "category")
    ArtworkDTO toDto(Artwork s);
}
