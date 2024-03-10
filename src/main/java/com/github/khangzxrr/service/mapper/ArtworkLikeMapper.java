package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.ArtworkLike;
import com.github.khangzxrr.service.dto.ArtworkLikeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkLike} and its DTO {@link ArtworkLikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkLikeMapper extends EntityMapper<ArtworkLikeDTO, ArtworkLike> {
    ArtworkLikeDTO toDto(ArtworkLike s);
}
