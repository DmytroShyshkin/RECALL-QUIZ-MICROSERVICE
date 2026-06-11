package com.dmytro.quiz_service.adapters.in.rest.dto;

import java.util.UUID;

public record QuizSessionResponse(
        UUID sessionId
        , int currentIndex
        , int totalQuestions
        , int score
        , boolean completed
        , QuizQuestionResponse currentQuestion
) {
}
