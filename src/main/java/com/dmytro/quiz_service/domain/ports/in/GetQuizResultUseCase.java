package com.dmytro.quiz_service.domain.ports.in.quizSessionPortIn;

import com.dmytro.quiz_service.domain.model.QuizSession;

import java.util.UUID;

public interface GetQuizResultUseCase {
    QuizSession getQuizResult(UUID sessionId);
}
