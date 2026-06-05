package com.dmytro.quiz_service.domain.ports.out;

import com.dmytro.quiz_service.domain.model.WordLearningProgress;

import java.util.Optional;
import java.util.UUID;

public interface UserProgressPort {
    WordLearningProgress save(WordLearningProgress progress);
    Optional<WordLearningProgress> findByuserAndWord(
            UUID userId
            , UUID wordId
            );
}
