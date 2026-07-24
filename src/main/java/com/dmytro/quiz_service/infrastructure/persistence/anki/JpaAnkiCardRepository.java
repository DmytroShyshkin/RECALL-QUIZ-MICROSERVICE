package com.dmytro.quiz_service.infrastructure.persistence.anki;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaAnkiCardRepository extends MongoRepository<AnkiCardDocument, UUID> {
    Optional<AnkiCardDocument> findByUserEmailAndId(String userEmail, UUID id);
    Optional<AnkiCardDocument> findByWordIdAndUserEmail(UUID wordId, String userEmail);
    Optional<AnkiCardDocument> deleteByWordIdAndUserEmail(UUID wordId, String userEmail);
    List<AnkiCardDocument> findByUserEmailAndNextReviewAtBefore(String userEmail, LocalDateTime before);
}
