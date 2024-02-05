package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkSelling} and its DTO {@link ArtworkSellingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkSellingMapper extends EntityMapper<ArtworkSellingDTO, ArtworkSelling> {}
