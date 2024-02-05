package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.ArtworkSelling;
import com.github.khangzxrr.domain.SellingBid;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
import com.github.khangzxrr.service.dto.SellingBidDTO;
import com.github.khangzxrr.service.dto.WalletTransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SellingBid} and its DTO {@link SellingBidDTO}.
 */
@Mapper(componentModel = "spring")
public interface SellingBidMapper extends EntityMapper<SellingBidDTO, SellingBid> {
    @Mapping(target = "transaction", source = "transaction", qualifiedByName = "walletTransactionId")
    @Mapping(target = "artworkSelling", source = "artworkSelling", qualifiedByName = "artworkSellingId")
    SellingBidDTO toDto(SellingBid s);

    @Named("walletTransactionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WalletTransactionDTO toDtoWalletTransactionId(WalletTransaction walletTransaction);

    @Named("artworkSellingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ArtworkSellingDTO toDtoArtworkSellingId(ArtworkSelling artworkSelling);
}
