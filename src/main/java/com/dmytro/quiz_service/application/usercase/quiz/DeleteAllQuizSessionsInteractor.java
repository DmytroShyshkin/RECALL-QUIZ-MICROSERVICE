package com.dmytro.quiz_service.application.usercase.quiz;

import com.dmytro.quiz_service.domain.ports.in.DeleteAllQuizSessionsUseCase;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteAllQuizSessionsInteractor implements DeleteAllQuizSessionsUseCase {
    private final QuizRepositoryPort quizRepositoryPort;

    @Override
    public void deleteAllQuizCards(String userEmail) {
        if (userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException("userEmail must not be null or blank");
        }

        UUID userId = UUID.nameUUIDFromBytes(userEmail.getBytes());

        quizRepositoryPort.deleteAllByUserId(userId);
    }
}
