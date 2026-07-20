package com.dmytro.quiz_service.adapters.in.rest.quiz.dto;

import java.util.List;
import java.util.UUID;

public record QuizSessionResponse(
        UUID sessionId
        , int currentIndex
        , int totalQuestions
        , int score
        , boolean completed
        , List<QuizQuestionResponse> correctAnswer
        , List<QuizQuestionResponse> wrongAnswer
        , QuizQuestionResponse currentQuestion
) {
}
