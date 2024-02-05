package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkComplain;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.service.dto.ArtworkComplainDTO;
import com.github.khangzxrr.service.dto.ArtworkDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkComplain} and its DTO {@link ArtworkComplainDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkComplainMapper extends EntityMapper<ArtworkComplainDTO, ArtworkComplain> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "artworkId")
    ArtworkComplainDTO toDto(ArtworkComplain s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("artworkId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkDTO toDtoArtworkId(Artwork artwork);
}
