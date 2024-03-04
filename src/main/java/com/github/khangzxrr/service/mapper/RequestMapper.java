package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.service.dto.CreateRequestDTO;
import com.github.khangzxrr.service.dto.MediaDTO;
import com.github.khangzxrr.service.dto.RequestAttachmentDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Request} and its DTO {@link RequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    RequestDTO toDto(Request s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserId(User user);

    RequestDTO toDto(CreateRequestDTO createRequestDTO);

    default RequestAttachmentDTO fromStringToAttachmentDTO(String attachmentUrl) {
        RequestAttachmentDTO requestAttachmentDTO = new RequestAttachmentDTO();

        MediaDTO mediaDTO = new MediaDTO();

        mediaDTO.setUrl(attachmentUrl);

        requestAttachmentDTO.setMedia(mediaDTO);

        return requestAttachmentDTO;
    }
}
