package com.dmytro.quiz_service.domain.ports.out;

import com.dmytro.quiz_service.domain.model.AnkiCard;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnkiCardPort {
    AnkiCard save(AnkiCard card);
    Optional<AnkiCard> findById(UUID id);
    List<AnkiCard> findDueCards(String userEmail, LocalDateTime before);
}
