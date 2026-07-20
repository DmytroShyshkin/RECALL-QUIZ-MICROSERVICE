package com.dmytro.quiz_service.adapters.in.rest.quiz.dto;

import java.util.UUID;

public record QuizSessionResponse(
        UUID sessionId
        , int currentIndex
        , int totalQuestions
        , int score
        , boolean completed
        , QuizQuestionResponse[] correctAnswer
        , QuizQuestionResponse[] wrongAnswer
        , QuizQuestionResponse currentQuestion
) {
}
