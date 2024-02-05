package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.ArtworkCategory;
import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkCategory} and its DTO {@link ArtworkCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkCategoryMapper extends EntityMapper<ArtworkCategoryDTO, ArtworkCategory> {}
