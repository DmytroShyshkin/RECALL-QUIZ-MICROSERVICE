package com.dmytro.quiz_service.adapters.out.persistence.words;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.dmytro.quiz_service.domain.model.WordSnapshot;
import com.dmytro.quiz_service.domain.ports.out.WordSnapshotPort;
import com.dmytro.quiz_service.infrastructure.persistence.words.JpaWordSnapshotRepository;
import com.dmytro.quiz_service.infrastructure.persistence.words.WordSnapshotDocument;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WordSnapshotRepositoryAdapter implements WordSnapshotPort {

    private final JpaWordSnapshotRepository repository;
    private final WordSnapshotMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public void upsert(WordSnapshot snapshot) {
        repository.save(mapper.toDocument(snapshot));
    }

    @Override
    public void deleteByWordId(UUID wordId) {
        repository.deleteByWordId(wordId);
    }

    @Override
    public void deleteAllByOwnerEmail(String ownerEmail) {
        repository.deleteAllByOwnerEmail(ownerEmail);
    }

    @Override
    public List<String> findDistinctOwnerEmails() {
        return mongoTemplate.findDistinct(new Query(), "ownerEmail", WordSnapshotDocument.class, String.class);
    }
}
