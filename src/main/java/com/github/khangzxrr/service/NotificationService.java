package com.github.khangzxrr.service;

import com.github.khangzxrr.domain.User;

public interface NotificationService {
    public void unsubcribeUsersFromTopic(String topic, User... users);

    public void subcribeUsersToTopic(String topic, User... users);

    public void sendToUser(String title, String body, User user);

    public void sendToUsers(String title, String body, User... users);

    public void sendToTopic(String topic, String title, String body);

    public void sendToWsTopic(String topic, Object payload);
}
