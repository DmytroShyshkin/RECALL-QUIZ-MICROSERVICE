package com.dmytro.quiz_service.infrastructure.kafka.word;

import java.util.UUID;

public record WordDeletedEvent(
        UUID wordId
        , String userEmail
) {
}
