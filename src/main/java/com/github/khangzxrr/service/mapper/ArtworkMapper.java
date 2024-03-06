package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.CreateArtworkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artwork} and its DTO {@link ArtworkDTO}.
 */
@Mapper(componentModel = "spring", uses = ArtworkAssetMapper.class)
public interface ArtworkMapper extends EntityMapper<ArtworkDTO, Artwork> {
    @Mapping(target = "artworkAssets", source = "assets")
    Artwork toEntity(CreateArtworkDTO dto);

    @Mapping(target = "artworkSelling", source = "artworkSelling")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "category", source = "category")
    ArtworkDTO toDto(Artwork s);
}
