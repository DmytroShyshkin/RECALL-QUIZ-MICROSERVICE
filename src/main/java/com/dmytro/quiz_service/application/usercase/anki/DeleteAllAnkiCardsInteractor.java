package com.dmytro.quiz_service.application.usercase.anki;

import com.dmytro.quiz_service.domain.ports.in.DeleteAllAnkiCardsUseCase;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAllAnkiCardsInteractor implements DeleteAllAnkiCardsUseCase {
    private final AnkiCardPort port;
    @Override
    public void deleteAllAnkiCards(String userEmail) {
        port.deleteAllByUserEmail(userEmail);
    }
}
