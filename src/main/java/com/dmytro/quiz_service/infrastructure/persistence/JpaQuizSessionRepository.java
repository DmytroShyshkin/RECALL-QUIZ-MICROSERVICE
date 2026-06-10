package com.dmytro.quiz_service.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaQuizSessionRepository extends MongoRepository<QuizSessionDocument, UUID> {
    Optional<QuizSessionDocument> findBySessionId(UUID sessionId);
}
