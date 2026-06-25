package com.dmytro.quiz_service.application.usercase.quiz;

import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.domain.ports.in.GetQuizResultUseCase;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetQuizResultInteractor implements GetQuizResultUseCase {

    private final QuizRepositoryPort quizRepository;

    @Override
    public QuizSession getQuizResult(UUID sessionId) {
        return quizRepository.findSessionById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));
    }
}