package com.dmytro.quiz_service.application.usercase.anki;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.in.ReviewAnkiUseCase;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import com.dmytro.quiz_service.domain.service.AnkiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReviewAnkiInteractor implements ReviewAnkiUseCase {

    private final AnkiCardPort ankiCardPort;
    private final AnkiService ankiService;

    @Override
    public AnkiCard review(UUID cardId, int rating, String userEmail) {
        AnkiCard card = ankiCardPort.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found: " + cardId));

        if (!card.getUserEmail().equals(userEmail)) {
            throw new AccessDeniedException("Not your card");
        }

        AnkiCard updatedCard = ankiService.applyFsrs(card, rating);
        return ankiCardPort.save(updatedCard);
    }
}