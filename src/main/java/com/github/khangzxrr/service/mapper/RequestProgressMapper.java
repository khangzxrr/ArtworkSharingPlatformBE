package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestProgress;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.dto.WalletTransactionDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.RequestProgressPaymentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestProgress} and its DTO {@link RequestProgressDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestProgressMapper extends EntityMapper<RequestProgressDTO, RequestProgress> {
    RequestProgress toEntity(RequestProgressPaymentDTO paymentDTO);

    @Mapping(target = "amount", source = "transaction.amount")
    RequestProgressPaymentDTO toPaymentDTO(RequestProgress rp);

    @Mapping(target = "transaction", source = "transaction", qualifiedByName = "walletTransactionId")
    @Mapping(target = "request", source = "request", qualifiedByName = "requestId")
    RequestProgressDTO toDto(RequestProgress s);

    @Named("walletTransactionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WalletTransactionDTO toDtoWalletTransactionId(WalletTransaction walletTransaction);

    @Named("requestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestDTO toDtoRequestId(Request request);
}
