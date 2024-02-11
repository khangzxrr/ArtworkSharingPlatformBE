package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Media;
import com.github.khangzxrr.domain.RequestProgress;
import com.github.khangzxrr.domain.RequestProgressAttachment;
import com.github.khangzxrr.service.dto.MediaDTO;
import com.github.khangzxrr.service.dto.RequestProgressAttachmentDTO;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestProgressAttachment} and its DTO {@link RequestProgressAttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestProgressAttachmentMapper extends EntityMapper<RequestProgressAttachmentDTO, RequestProgressAttachment> {
    @Mapping(target = "media", source = "media", qualifiedByName = "mediaId")
    RequestProgressAttachmentDTO toDto(RequestProgressAttachment s);

    @Named("mediaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MediaDTO toDtoMediaId(Media media);

    @Named("requestProgressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestProgressDTO toDtoRequestProgressId(RequestProgress requestProgress);
}
