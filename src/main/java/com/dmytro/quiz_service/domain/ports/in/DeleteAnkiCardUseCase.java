package com.dmytro.quiz_service.domain.ports.in;

import com.dmytro.quiz_service.domain.model.AnkiCard;

import java.util.Optional;
import java.util.UUID;

public interface DeleteAnkiCardUseCase {
    Optional<AnkiCard> deleteAnkiCard(UUID wordId, String userEmail);
}
