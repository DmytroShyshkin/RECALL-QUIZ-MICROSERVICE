package com.dmytro.quiz_service.infrastructure.kafka.producer.ankiCardReviewed;

import java.util.UUID;

public record AnkiCardReviewedEvent(
        UUID cardId
        , UUID wordId
        , String userEmail
        , int rating
        , String newState
) {}