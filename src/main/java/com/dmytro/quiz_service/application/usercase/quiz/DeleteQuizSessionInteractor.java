package com.dmytro.quiz_service.application.usercase.quiz;

import com.dmytro.quiz_service.domain.ports.in.DeleteQuizSessionUseCase;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteQuizSessionInteractor implements DeleteQuizSessionUseCase {

    private final QuizRepositoryPort quizRepository;

    @Override
    public void deleteSession(UUID sessionId) {
        quizRepository.deleteById(sessionId);
    }
}