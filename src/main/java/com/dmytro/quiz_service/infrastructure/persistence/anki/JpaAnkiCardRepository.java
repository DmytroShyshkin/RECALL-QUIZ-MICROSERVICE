package com.dmytro.quiz_service.infrastructure.persistence.anki;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaAnkiCardRepository extends MongoRepository<AnkiCardDocument, UUID> {
    Optional<AnkiCardDocument> findByUserEmailAndId(String userEmail, UUID id);
    List<AnkiCardDocument> findByUserEmailAndNextReviewAtBefore(String userEmail, LocalDateTime before);
}
