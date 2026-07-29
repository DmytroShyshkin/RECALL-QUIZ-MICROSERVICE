package com.dmytro.quiz_service.infrastructure.kafka.consumer.wordDeleted;

import java.util.UUID;

public record WordDeletedEvent(
        UUID wordId
        , String userEmail
) {
}
