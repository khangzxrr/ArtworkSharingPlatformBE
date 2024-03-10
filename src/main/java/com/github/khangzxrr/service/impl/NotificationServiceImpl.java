package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.UserNotifyToken;
import com.github.khangzxrr.service.NotificationService;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final FirebaseMessaging firebaseMessaging;
    private final SimpMessageSendingOperations messagingTemplate;

    public NotificationServiceImpl(FirebaseMessaging firebaseMessaging, SimpMessageSendingOperations messagingTemplate) {
        this.firebaseMessaging = firebaseMessaging;
        this.messagingTemplate = messagingTemplate;
    }

    public List<String> mapUsersToTokens(@NotNull User[] users) {
        List<UserNotifyToken> notifitokens = Arrays.stream(users).map(u -> u.getNotifyTokens()).flatMap(Set::stream).toList();

        List<String> tokens = notifitokens.stream().map(n -> n.getToken()).toList();

        return tokens;
    }

    @Override
    public void sendToUser(String title, String body, User user) {
        for (UserNotifyToken userNotifyToken : user.getNotifyTokens()) {
            Notification notification = Notification.builder().setTitle(title).setBody(body).build();

            Message message = Message.builder().setToken(userNotifyToken.getToken()).setNotification(notification).build();

            ApiFuture<String> future = firebaseMessaging.sendAsync(message);

            ApiFutures.addCallback(
                future,
                new ApiFutureCallback<String>() {
                    @Override
                    public void onFailure(Throwable t) {
                        log.error(t.getMessage(), t);
                    }

                    @Override
                    public void onSuccess(String result) {
                        log.info(result);
                    }
                },
                Runnable::run
            );
        }
    }

    @Override
    public void sendToUsers(@NotBlank String title, @NotBlank String body, @NotNull User... users) {
        List<String> tokens = mapUsersToTokens(users);

        Notification notification = Notification.builder().setTitle(title).setBody(body).build();

        MulticastMessage message = MulticastMessage.builder().setNotification(notification).addAllTokens(tokens).build();

        ApiFuture<BatchResponse> future = firebaseMessaging.sendEachForMulticastAsync(message);

        ApiFutures.addCallback(
            future,
            new ApiFutureCallback<BatchResponse>() {
                @Override
                public void onFailure(Throwable t) {
                    log.error(t.getMessage(), t);
                }

                @Override
                public void onSuccess(BatchResponse result) {
                    log.info(result.toString());
                }
            },
            Runnable::run
        );
    }

    @Override
    public void subcribeUsersToTopic(@NotBlank String topic, @NotNull User... users) {
        List<String> tokens = mapUsersToTokens(users);

        ApiFuture<TopicManagementResponse> future = firebaseMessaging.subscribeToTopicAsync(tokens, topic);

        ApiFutures.addCallback(
            future,
            new ApiFutureCallback<TopicManagementResponse>() {
                @Override
                public void onFailure(Throwable t) {
                    log.error(t.getMessage(), t);
                }

                @Override
                public void onSuccess(TopicManagementResponse result) {
                    log.info(result.toString());
                }
            },
            Runnable::run
        );
    }

    @Override
    public void unsubcribeUsersFromTopic(String topic, User... users) {
        List<String> tokens = mapUsersToTokens(users);

        ApiFuture<TopicManagementResponse> future = firebaseMessaging.unsubscribeFromTopicAsync(tokens, topic);

        ApiFutures.addCallback(
            future,
            new ApiFutureCallback<TopicManagementResponse>() {
                @Override
                public void onFailure(Throwable t) {
                    log.error(t.getMessage(), t);
                }

                @Override
                public void onSuccess(TopicManagementResponse result) {
                    log.info(result.toString());
                }
            },
            Runnable::run
        );
    }

    @Override
    public void sendToTopic(@NotBlank String topic, String title, String body) {
        Notification notification = Notification.builder().setTitle(title).setBody(body).build();

        Message message = Message.builder().setNotification(notification).setTopic(topic).build();

        ApiFuture<String> future = firebaseMessaging.sendAsync(message);
        ApiFutures.addCallback(
            future,
            new ApiFutureCallback<String>() {
                @Override
                public void onFailure(Throwable t) {
                    log.error(t.getMessage(), t);
                }

                @Override
                public void onSuccess(String result) {
                    log.info(result);
                }
            },
            Runnable::run
        );
    }

    @Override
    @Async
    public void sendToWsTopic(String topic, Object payload) {
        try {
            messagingTemplate.convertAndSend(topic, payload);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
