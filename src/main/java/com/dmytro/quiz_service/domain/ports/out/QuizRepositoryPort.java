package com.dmytro.quiz_service.domain.ports.out;

import com.dmytro.quiz_service.domain.model.QuizSession;

import java.util.Optional;
import java.util.UUID;

public interface QuizRepositoryPort {
    QuizSession save(QuizSession session);
    Optional<QuizSession> findSessionById(UUID sessionId);
    void deleteById(UUID sessionId);
}
