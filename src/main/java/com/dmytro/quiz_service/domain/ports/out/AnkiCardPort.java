package com.dmytro.quiz_service.domain.ports.out;

import com.dmytro.quiz_service.domain.model.AnkiCard;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AnkiCardPort {
    AnkiCard save(AnkiCard card);
    List<AnkiCard> findDueCards( // SM-2 algorithm
            UUID userId
            , LocalDateTime before
    );
}
