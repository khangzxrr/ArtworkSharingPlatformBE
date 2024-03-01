package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.Request;
import com.github.khangzxrr.domain.RequestChat;
import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.enumeration.RequestStatus;
import com.github.khangzxrr.repository.RequestChatRepository;
import com.github.khangzxrr.service.RequestChatService;
import com.github.khangzxrr.service.RequestService;
import com.github.khangzxrr.service.UserService;
import com.github.khangzxrr.service.dto.requestChatDTOs.CreateRequestChatDTO;
import com.github.khangzxrr.service.dto.requestChatDTOs.RequestChatDTO;
import com.github.khangzxrr.service.mapper.RequestChatMapper;
import com.github.khangzxrr.web.rest.errors.NotLoggedException;
import com.github.khangzxrr.web.rest.errors.RequestIsNotInCorrectState;
import com.github.khangzxrr.web.rest.errors.RequestNotBelongToAudienceException;
import com.github.khangzxrr.web.rest.errors.RequestNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestChatServiceImpl implements RequestChatService {

    private RequestChatRepository requestChatRepository;
    private RequestChatMapper requestChatMapper;
    private UserService userService;
    private RequestService requestService;

    public RequestChatServiceImpl(
        RequestChatRepository requestChatRepository,
        RequestChatMapper requestChatMapper,
        UserService userService,
        RequestService requestService
    ) {
        this.requestChatRepository = requestChatRepository;
        this.requestChatMapper = requestChatMapper;
        this.userService = userService;
        this.requestService = requestService;
    }

    @Override
    public List<RequestChatDTO> getAll(long requestId) {
        return requestChatRepository.findAllByRequestId(requestId).stream().map(requestChatMapper::toDto).toList();
    }

    @Override
    public List<RequestChatDTO> getAllAfterId(long requestId, long requestChatId) {
        return requestChatRepository
            .findAllByIdGreaterThanAndRequestId(requestChatId, requestId)
            .stream()
            .map(requestChatMapper::toDto)
            .toList();
    }

    @Override
    public RequestChatDTO create(long requestId, CreateRequestChatDTO requestChatDTO) {
        Optional<User> user = userService.getUserWithAuthorities();

        if (!user.isPresent()) {
            throw new NotLoggedException();
        }

        Optional<Request> requestOptional = requestService.getOne(requestId);

        if (!requestOptional.isPresent()) {
            throw new RequestNotFoundException();
        }

        Request request = requestOptional.get();

        if (
            request.getStatus() == RequestStatus.ON_BIDING ||
            request.getStatus() == RequestStatus.FAILED ||
            request.getStatus() == RequestStatus.ENDED
        ) {
            throw new RequestIsNotInCorrectState();
        }

        if (request.getUser() != user.get() && request.getSelectedBidUser().get() != user.get()) {
            throw new RequestNotBelongToAudienceException();
        }

        RequestChat requestChat = requestChatMapper.toEntity(requestChatDTO);
        requestChat.setFromUser(user.get());
        requestChat.setRequest(request);

        requestChat = requestChatRepository.save(requestChat);

        return requestChatMapper.toDto(requestChat);
    }
}
