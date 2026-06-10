package com.dmytro.quiz_service.domain.ports.in;

import com.dmytro.quiz_service.domain.model.AnkiCard;

import java.util.UUID;

public interface ReviewAnkiUseCase {
    AnkiCard review(UUID cardId, int rating); // rating: 1-4
}
