package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.service.dto.CreateRequestBidDTO;
import com.github.khangzxrr.service.dto.RequestBidDTO;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestBid} and its DTO {@link RequestBidDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestBidMapper extends EntityMapper<RequestBidDTO, RequestBid> {
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "duration", source = "duration")
    RequestBidDTO toDto(CreateRequestBidDTO createRequestBidDTO);

    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "request", source = "request", qualifiedByName = "requestId")
    RequestBidDTO toDto(RequestBid s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserId(User user);

    @Named("requestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestDTO toDtoRequestId(Request request);
}
