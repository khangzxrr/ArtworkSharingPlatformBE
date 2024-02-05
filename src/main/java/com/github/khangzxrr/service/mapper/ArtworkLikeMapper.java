package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkLike;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.service.dto.ArtworkDTO;
import com.github.khangzxrr.service.dto.ArtworkLikeDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkLike} and its DTO {@link ArtworkLikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkLikeMapper extends EntityMapper<ArtworkLikeDTO, ArtworkLike> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "userId")
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "artworkId")
    ArtworkLikeDTO toDto(ArtworkLike s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("artworkId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkDTO toDtoArtworkId(Artwork artwork);
}
