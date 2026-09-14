package com.dmytro.quiz_service.adapters.out.persistence.words;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dmytro.quiz_service.domain.model.WordSnapshot;
import com.dmytro.quiz_service.domain.ports.out.WordSnapshotPort;
import com.dmytro.quiz_service.infrastructure.persistence.words.JpaWordSnapshotRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WordSnapshotRepositoryAdapter implements WordSnapshotPort {

    private final JpaWordSnapshotRepository repository;
    private final WordSnapshotMapper mapper;

    @Override
    public void upsert(WordSnapshot snapshot) {
        repository.save(mapper.toDocument(snapshot));
    }

    @Override
    public void deleteByWordId(UUID wordId) {
        repository.deleteByWordId(wordId);
    }
}
