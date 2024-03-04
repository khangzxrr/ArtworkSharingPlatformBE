package com.github.khangzxrr.service.mapper;

import com.github.khangzxrr.domain.Media;
import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestProgress;
import com.github.khangzxrr.domain.RequestProgressAttachment;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.service.dto.RequestDTO;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.dto.WalletTransactionDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.CreateRequestProgressReportDTO;
import com.github.khangzxrr.service.dto.requestProgressDto.RequestProgressPaymentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestProgress} and its DTO {@link RequestProgressDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestProgressMapper extends EntityMapper<RequestProgressDTO, RequestProgress> {
    RequestProgress toEntity(RequestProgressPaymentDTO paymentDTO);

    RequestProgress toEntity(CreateRequestProgressReportDTO createRequestProgressReportDTO);

    @Mapping(target = "amount", source = "transaction.amount")
    RequestProgressPaymentDTO toPaymentDTO(RequestProgress rp);

    @Mapping(target = "transaction", source = "transaction", qualifiedByName = "walletTransactionId")
    @Mapping(target = "request", source = "request", qualifiedByName = "requestId")
    @Mapping(target = "attachments", source = "attachments")
    RequestProgressDTO toDto(RequestProgress s);

    @Named("walletTransactionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "amount", source = "amount")
    WalletTransactionDTO toDtoWalletTransactionId(WalletTransaction walletTransaction);

    @Named("requestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestDTO toDtoRequestId(Request request);

    // default String fromRequestProgressAttachment(RequestProgressAttachment requestProgressAttachment) {
    //     return requestProgressAttachment.getMedia().getUrl();
    // }

    default RequestProgressAttachment fromStringToRequestProgressAttachment(String attachmentUrl) {
        RequestProgressAttachment requestProgressAttachment = new RequestProgressAttachment();

        Media media = new Media();
        media.setUrl(attachmentUrl);

        requestProgressAttachment.setMedia(media);

        return requestProgressAttachment;
    }
}
