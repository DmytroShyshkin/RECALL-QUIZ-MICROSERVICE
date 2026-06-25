package com.dmytro.quiz_service.domain.ports.in;

import com.dmytro.quiz_service.domain.model.QuizSession;

import java.util.UUID;

public interface AnswerQuizUseCase {
    QuizSession answerQuiz(
            UUID sessionId
            , String userAnswer
    );
}
