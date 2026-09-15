package com.dmytro.quiz_service.domain.ports.out;

import java.util.List;
import java.util.UUID;

import com.dmytro.quiz_service.domain.model.WordSnapshot;

public interface WordSnapshotPort {
    void upsert(WordSnapshot snapshot);
    void deleteByWordId(UUID wordId);
    void deleteAllByOwnerEmail(String ownerEmail);
    List<String> findDistinctOwnerEmails();
}
