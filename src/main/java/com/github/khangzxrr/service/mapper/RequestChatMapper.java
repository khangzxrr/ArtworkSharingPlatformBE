package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.RequestChat;
import com.github.khangzxrr.service.dto.requestChatDTOs.CreateRequestChatDTO;
import com.github.khangzxrr.service.dto.requestChatDTOs.RequestChatDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestChatMapper extends EntityMapper<RequestChatDTO, RequestChat> {
    RequestChat toEntity(CreateRequestChatDTO createRequestChatDTO);
    RequestChatDTO toDto(RequestChat rc);
}
