package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.ArtworkComment;
import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkComment} and its DTO {@link ArtworkCommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkCommentMapper extends EntityMapper<ArtworkCommentDTO, ArtworkComment> {
    ArtworkCommentDTO toDto(ArtworkComment s);
}
