package com.dmytro.quiz_service.infrastructure.MongoDegub;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EnvChecker {
    @PostConstruct
    public void check() {
        boolean has = System.getenv("MONGODB_URI") != null && !System.getenv("MONGODB_URI").isBlank();
        System.out.println("MONGODB_URI present in env: " + has);
    }
}