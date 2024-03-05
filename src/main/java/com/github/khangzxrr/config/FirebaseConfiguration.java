package com.github.khangzxrr.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfiguration {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        FileInputStream refreshToken = new FileInputStream("src/main/resources/artwork-56ea5-firebase-adminsdk-zmzka-8475662013.json");

        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(refreshToken)).build();

        FirebaseApp app = FirebaseApp.initializeApp(options, "app");
        return FirebaseMessaging.getInstance(app);
    }
}
