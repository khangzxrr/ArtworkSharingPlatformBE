package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Certificate;
import com.github.khangzxrr.domain.Media;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.service.dto.CertificateDTO;
import com.github.khangzxrr.service.dto.MediaDTO;
import com.github.khangzxrr.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Certificate} and its DTO {@link CertificateDTO}.
 */
@Mapper(componentModel = "spring")
public interface CertificateMapper extends EntityMapper<CertificateDTO, Certificate> {
    @Mapping(target = "media", source = "media", qualifiedByName = "mediaId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    CertificateDTO toDto(Certificate s);

    @Named("mediaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MediaDTO toDtoMediaId(Media media);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
