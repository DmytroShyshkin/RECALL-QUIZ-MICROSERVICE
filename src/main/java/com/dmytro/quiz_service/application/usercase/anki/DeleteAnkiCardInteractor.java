package com.dmytro.quiz_service.application.usercase.anki;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.in.DeleteAnkiCardUseCase;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteAnkiCardInteractor implements DeleteAnkiCardUseCase {
    private final AnkiCardPort ankiCardPort;

    @Override
    public Optional<AnkiCard> deleteAnkiCard(UUID wordId, String userEmail) {
        return ankiCardPort.deleteByWordIdAndUserEmail(wordId, userEmail);
    }
}
