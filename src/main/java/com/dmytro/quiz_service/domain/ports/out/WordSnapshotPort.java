package com.dmytro.quiz_service.domain.ports.out;

import java.util.UUID;

import com.dmytro.quiz_service.domain.model.WordSnapshot;

public interface WordSnapshotPort {
    void upsert(WordSnapshot snapshot);
    void deleteByWordId(UUID wordId);
}
