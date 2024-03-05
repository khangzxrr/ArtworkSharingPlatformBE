package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.domain.User;
import com.github.khangzxrr.domain.UserNotifyToken;
import com.github.khangzxrr.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.transaction.Transactional;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public NotificationServiceImpl(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
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
}
