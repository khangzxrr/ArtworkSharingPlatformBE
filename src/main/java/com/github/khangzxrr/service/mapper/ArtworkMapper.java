package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Artwork;
import com.github.khangzxrr.domain.ArtworkComment;
import com.github.khangzxrr.domain.ArtworkLike;
import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
import com.github.khangzxrr.service.dto.artworkDTOs.CreateArtworkDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artwork} and its DTO {@link ArtworkDTO}.
 */
@Mapper(componentModel = "spring", uses = { ArtworkAssetMapper.class, ArtworkCommentMapper.class })
public interface ArtworkMapper extends EntityMapper<ArtworkDTO, Artwork> {
    @Mapping(target = "artworkAssets", source = "assets")
    Artwork toEntity(CreateArtworkDTO dto);

    @Mapping(target = "artworkSelling", source = "artworkSelling")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "commentsCount", source = "comments")
    @Mapping(target = "likesCount", source = "likes")
    @Mapping(target = "artworkComments", source = "comments")
    ArtworkDTO toDto(Artwork s);

    default long mapLikesCount(Set<ArtworkLike> likes) {
        return likes.size();
    }

    default long mapCommentsCount(Set<ArtworkComment> comments) {
        return comments.size();
    }
}
