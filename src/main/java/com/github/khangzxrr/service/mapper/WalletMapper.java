package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.service.dto.UserDTO;
import com.github.khangzxrr.service.dto.WalletDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wallet} and its DTO {@link WalletDTO}.
 */
@Mapper(componentModel = "spring")
public interface WalletMapper extends EntityMapper<WalletDTO, Wallet> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    WalletDTO toDto(Wallet s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
