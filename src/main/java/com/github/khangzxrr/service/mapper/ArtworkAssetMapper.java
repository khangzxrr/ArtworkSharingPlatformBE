package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.ArtworkAsset;
import com.github.khangzxrr.service.dto.ArtworkAssetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArtworkAsset} and its DTO {@link ArtworkAssetDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtworkAssetMapper extends EntityMapper<ArtworkAssetDTO, ArtworkAsset> {
    ArtworkAssetDTO toDto(ArtworkAsset s);
}
