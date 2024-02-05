package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkAsset;
import com.github.khangzxrr.domain.Media;
import com.github.khangzxrr.service.dto.ArtworkAssetDTO;
import com.github.khangzxrr.service.dto.ArtworkDTO;
import com.github.khangzxrr.service.dto.MediaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkAsset} and its DTO {@link ArtworkAssetDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkAssetMapper extends EntityMapper<ArtworkAssetDTO, ArtworkAsset> {
    @Mapping(target = "media", source = "media", qualifiedByName = "mediaId")
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "artworkId")
    ArtworkAssetDTO toDto(ArtworkAsset s);

    @Named("mediaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MediaDTO toDtoMediaId(Media media);

    @Named("artworkId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkDTO toDtoArtworkId(Artwork artwork);
}
