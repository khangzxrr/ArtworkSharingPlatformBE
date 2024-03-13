package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.SellingBid;
import com.github.khangzxrr.service.dto.SellingBidDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SellingBid} and its DTO {@link SellingBidDTO}.
 */
@Mapper(componentModel = "spring")
public interface SellingBidMapper extends EntityMapper<SellingBidDTO, SellingBid> {
    SellingBidDTO toDto(SellingBid s);
}
