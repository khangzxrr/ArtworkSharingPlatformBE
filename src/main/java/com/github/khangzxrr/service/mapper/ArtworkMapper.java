package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.service.ArtworkLikeService;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.CreateArtworkDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link Artwork} and its DTO {@link ArtworkDTO}.
 */
@Mapper(componentModel = "spring", uses = { ArtworkAssetMapper.class, ArtworkLikeService.class })
public abstract class ArtworkMapper {

    @Autowired
    protected ArtworkLikeService artworkLikeService;

    @Mapping(target = "artworkAssets", source = "assets")
    public abstract Artwork toEntity(CreateArtworkDTO dto);

    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "commentsCount", expression = "java(source.getComments().size())")
    @Mapping(target = "likesCount", expression = "java(source.getLikes().size())")
    @Mapping(target = "userLikedThisArtwork", expression = "java(artworkLikeService.getLikeByUser(source.getId()).isPresent())")
    public abstract ArtworkDTO toDto(Artwork source);
}
