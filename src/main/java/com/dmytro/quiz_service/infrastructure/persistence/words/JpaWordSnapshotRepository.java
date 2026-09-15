package com.dmytro.quiz_service.infrastructure.persistence.words;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JpaWordSnapshotRepository extends MongoRepository<WordSnapshotDocument, UUID> {
    Optional<WordSnapshotDocument> findByWordIdAndOwnerEmail(UUID wordId, String ownerEmail);
    List<WordSnapshotDocument> findAllByOwnerEmail(String ownerEmail);
    void deleteByWordId(UUID wordId);
    void deleteAllByOwnerEmail(String ownerEmail);
}
