package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.UserNotifyToken;
import com.github.khangzxrr.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final FirebaseMessaging firebaseMessaging;

    public NotificationServiceImpl(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public List<String> mapUsersToTokens(@NotNull User[] users) {
        List<UserNotifyToken> notifitokens = Arrays.stream(users).map(u -> u.getNotifyTokens()).flatMap(Set::stream).toList();

        List<String> tokens = notifitokens.stream().map(n -> n.getToken()).toList();

        return tokens;
    }

    @Override
    public void sendToUser(Map<String, String> data, User user) {
        for (UserNotifyToken userNotifyToken : user.getNotifyTokens()) {
            try {
                Notification notification = Notification.builder().setTitle("Artwork sharing platform").setBody(data.get("body")).build();

                Message message = Message.builder().setToken(userNotifyToken.getToken()).setNotification(notification).build();

                firebaseMessaging.send(message);
            } catch (FirebaseMessagingException firebaseMessagingException) {}
        }
    }

    @Override
    public void sendToUsers(@NotBlank String title, @NotBlank String body, @NotNull User... users) {
        List<String> tokens = mapUsersToTokens(users);

        Notification notification = Notification.builder().setTitle(title).setBody(body).build();

        MulticastMessage message = MulticastMessage.builder().setNotification(notification).addAllTokens(tokens).build();

        try {
            firebaseMessaging.sendEachForMulticast(message);
        } catch (FirebaseMessagingException ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void subcribeUsersToTopic(@NotBlank String topic, @NotNull User... users) {
        List<String> tokens = mapUsersToTokens(users);

        try {
            firebaseMessaging.subscribeToTopic(tokens, topic);
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
