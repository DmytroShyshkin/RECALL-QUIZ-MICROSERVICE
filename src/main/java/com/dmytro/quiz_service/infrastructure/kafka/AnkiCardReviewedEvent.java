package com.dmytro.quiz_service.infrastructure.kafka;

import java.util.UUID;

public record AnkiCardReviewedEvent(
        UUID cardId,
        String userEmail,
        int rating,
        String newState
) {}