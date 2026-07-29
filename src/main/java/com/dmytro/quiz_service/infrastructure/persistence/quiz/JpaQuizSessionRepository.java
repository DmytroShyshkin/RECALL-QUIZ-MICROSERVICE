package com.dmytro.quiz_service.infrastructure.persistence.quiz;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaQuizSessionRepository extends MongoRepository<QuizSessionDocument, UUID> {
    void deleteAllByUserId(UUID userId);
}
