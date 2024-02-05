package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Media;
import com.github.khangzxrr.service.dto.MediaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Media} and its DTO {@link MediaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {}
