package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkCategory;
import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
import com.github.khangzxrr.service.dto.ArtworkDTO;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artwork} and its DTO {@link ArtworkDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkMapper extends EntityMapper<ArtworkDTO, Artwork> {
    @Mapping(target = "artworkSelling", source = "artworkSelling", qualifiedByName = "artworkSellingId")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "userId")
    @Mapping(target = "category", source = "category", qualifiedByName = "artworkCategoryId")
    ArtworkDTO toDto(Artwork s);

    @Named("artworkSellingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkSellingDTO toDtoArtworkSellingId(ArtworkSelling artworkSelling);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("artworkCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkCategoryDTO toDtoArtworkCategoryId(ArtworkCategory artworkCategory);
}
