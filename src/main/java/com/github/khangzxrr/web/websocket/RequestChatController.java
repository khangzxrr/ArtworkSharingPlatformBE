package com.github.khangzxrr.web.websocket;

import com.github.khangzxrr.service.RequestChatService;
import com.github.khangzxrr.service.dto.requestChatDTOs.CreateRequestChatDTO;
import com.github.khangzxrr.service.dto.requestChatDTOs.RequestChatDTO;
import jakarta.validation.Valid;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class RequestChatController {

    private final RequestChatService requestChatService;
    private final SimpMessageSendingOperations messagingTemplate;

    public RequestChatController(RequestChatService requestChatService, SimpMessageSendingOperations messagingTemplate) {
        this.requestChatService = requestChatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/topic/requests/{requestId}/chats")
    @SendTo("/topic/requests/{requestId}/chats-notification")
    public RequestChatDTO sendGreeting(@DestinationVariable long requestId, @Valid @Payload CreateRequestChatDTO CreateRequestChatDTO)
        throws Exception {
        RequestChatDTO requestChatDTO = requestChatService.create(requestId, CreateRequestChatDTO);

        return requestChatDTO;
    }
}
