package com.dmytro.quiz_service.infrastructure.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MongoEnvChecker {

    private final Environment env;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        String uri = env.getProperty("spring.data.mongodb.uri");
        boolean fromEnv = System.getenv("MONGODB_URI") != null;
        String masked = uri == null ? "null" : uri.replaceAll(":(.*)@", ":*****@");
        System.out.println("spring.data.mongodb.uri resolved: " + masked);
        System.out.println("MONGODB_URI present in env: " + fromEnv);
    }
}