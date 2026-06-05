package com.dmytro.quiz_service.domain.ports.in;

import com.dmytro.quiz_service.domain.model.QuizSession;

import java.util.List;
import java.util.UUID;

public interface StartQuizUseCase {
    QuizSession startQuiz(
            UUID userId
            , List<String> language
            , int wordCount
    );
}
