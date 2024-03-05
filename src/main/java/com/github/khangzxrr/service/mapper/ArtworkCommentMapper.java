package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkComment;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import com.github.khangzxrr.service.dto.ArtworkDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link ArtworkComment} and its DTO {@link ArtworkCommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkCommentMapper extends EntityMapper<ArtworkCommentDTO, ArtworkComment> {
    ArtworkCommentMapper INSTANCE = Mappers.getMapper(ArtworkCommentMapper.class);

    @Mapping(target = "owner", source = "owner", qualifiedByName = "userId")
    @Mapping(target = "artwork", source = "artwork", qualifiedByName = "artworkId")
    ArtworkCommentDTO toDto(ArtworkComment s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("artworkId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkDTO toDtoArtworkId(Artwork artwork);

    @Named("toDtoSkipArtwork")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "userId")
    @Mapping(target = "artwork", ignore = true)
    ArtworkCommentDTO toDtoSkipArtwork(ArtworkComment s);
}
