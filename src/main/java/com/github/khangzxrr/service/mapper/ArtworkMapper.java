package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkCategory;
import com.github.khangzxrr.domain.ArtworkComment;
import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import com.github.khangzxrr.service.dto.ArtworkDTO;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Artwork} and its DTO {@link ArtworkDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkMapper extends EntityMapper<ArtworkDTO, Artwork> {
    ArtworkMapper INSTANCE = Mappers.getMapper(ArtworkMapper.class);
    ArtworkCommentMapper oArtworkCommentMapper = Mappers.getMapper(ArtworkCommentMapper.class);

    @Mapping(target = "artworkSelling", source = "artworkSelling", qualifiedByName = "artworkSellingId")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "userId")
    @Mapping(target = "category", source = "category", qualifiedByName = "artworkCategoryId")
    ArtworkDTO toDto(Artwork s);

    @Named("artworkSellingId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    ArtworkSellingDTO toDtoArtworkSellingId(ArtworkSelling artworkSelling);

    @Named("userId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("artworkCategoryId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    ArtworkCategoryDTO toDtoArtworkCategoryId(ArtworkCategory artworkCategory);

    @Named("toDtoPost")
    @Mappings(
        {
            @Mapping(target = "artworkComment", source = "comments", qualifiedByName = "mapToArtworkCommentDTOSet"),
            @Mapping(target = "artworkSelling", ignore = true),
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "category", ignore = true),
        }
    )
    ArtworkDTO toDtoPost(Artwork s);

    @Named("mapToArtworkCommentDTOSet")
    static Set<ArtworkCommentDTO> mapToArtworkCommentDTOSet(Set<ArtworkComment> cmt) {
        return cmt
            .stream()
            .map(artworkComment -> {
                if (artworkComment == null) {
                    return null;
                }
                return ArtworkCommentMapper.INSTANCE.toDtoSkipArtwork(artworkComment);
            })
            .collect(Collectors.toSet());
    }
    // @Named("mapToArtworkCommentDTOSet")
    // @BeanMapping(ignoreByDefault = false)
    // @Mapping(target = "id", source = "id")
    // ArtworkCommentDTO mapToArtworkCommentDTOSet(ArtworkComment artworkComment);

}
