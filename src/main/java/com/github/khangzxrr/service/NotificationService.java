package com.github.khangzxrr.service;

import com.github.khangzxrr.domain.User;
import java.util.Map;

public interface NotificationService {
    public void sendToUser(Map<String, String> data, User user);
}
