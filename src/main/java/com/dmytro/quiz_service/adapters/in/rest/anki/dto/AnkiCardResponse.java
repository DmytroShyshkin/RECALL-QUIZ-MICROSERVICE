package com.dmytro.quiz_service.adapters.in.rest.anki.dto;

import com.dmytro.quiz_service.domain.model.CardState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AnkiCardResponse(
        UUID id
        , UUID wordId
        , String word
        , List<String> translations
        , CardState state
        , double retrievability
        , LocalDateTime nextReviewAt
) {
}
