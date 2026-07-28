package com.dmytro.quiz_service.domain.ports.in;

public interface DeleteAllQuizSessionsUseCase {
    void deleteAllQuizCards(String userEmail);
}
