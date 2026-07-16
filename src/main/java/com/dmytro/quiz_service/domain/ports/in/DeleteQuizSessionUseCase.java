package com.dmytro.quiz_service.domain.ports.in;

import java.util.UUID;

public interface DeleteQuizSessionUseCase {
    void deleteSession(UUID sessionId);
}