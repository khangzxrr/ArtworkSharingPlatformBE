package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Media;
import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestAttachment;
import com.github.khangzxrr.service.dto.MediaDTO;
import com.github.khangzxrr.service.dto.RequestAttachmentDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestAttachment} and its DTO {@link RequestAttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestAttachmentMapper extends EntityMapper<RequestAttachmentDTO, RequestAttachment> {
    @Mapping(target = "media", source = "media", qualifiedByName = "mediaId")
    RequestAttachmentDTO toDto(RequestAttachment s);

    @Named("mediaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MediaDTO toDtoMediaId(Media media);
}
