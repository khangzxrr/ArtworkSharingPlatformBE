package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.service.ArtworkLikeService;
import com.github.khangzxrr.service.ArtworkSellingService;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.CreateArtworkDTO;
import java.util.Optional;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Artwork} and its DTO {@link ArtworkDTO}.
 */
@Mapper(componentModel = "spring")
public abstract class ArtworkMapper {

    @Autowired
    protected ArtworkLikeService artworkLikeService;

    @Autowired
    protected ArtworkSellingService artworkSellingService;

    @Autowired
    protected ArtworkSellingMapper artworkSellingMapper;

    @Mapping(target = "artworkAssets", source = "assets")
    public abstract Artwork toEntity(CreateArtworkDTO dto);

    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "commentsCount", expression = "java(source.getComments().size())")
    @Mapping(target = "likesCount", expression = "java(source.getLikes().size())")
    @Mapping(target = "userLikedThisArtwork", expression = "java(artworkLikeService.getLikeByUser(source.getId()).isPresent())")
    @Mapping(target = "onGoingArtworkSelling", source = "source.id", qualifiedByName = "mapArtworkIsSelling")
    public abstract ArtworkDTO toDto(Artwork source);

    @Named("mapArtworkIsSelling")
    public ArtworkSellingDTO mapArtworkIsSelling(Long artworkId) {
        Optional<ArtworkSellingDTO> artworkSellingDto = artworkSellingService
            .getOnGoingSellingByArtworkId(artworkId)
            .map(artworkSellingMapper::toDto);

        if (artworkSellingDto.isPresent()) {
            return artworkSellingDto.get();
        }

        return null;
    }
}
