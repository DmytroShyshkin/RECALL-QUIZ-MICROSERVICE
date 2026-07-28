package com.dmytro.quiz_service.domain.ports.in;

public interface DeleteAllAnkiCardsUseCase {
    void deleteAllAnkiCards(String userEmail);
}
