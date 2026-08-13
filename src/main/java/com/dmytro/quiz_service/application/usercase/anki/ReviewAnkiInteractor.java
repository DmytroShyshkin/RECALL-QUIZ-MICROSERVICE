package com.dmytro.quiz_service.application.usercase.anki;

import com.dmytro.quiz_service.domain.exception.AnkiCardNotFoundException;
import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.in.ReviewAnkiUseCase;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import com.dmytro.quiz_service.domain.service.AnkiService;
import com.dmytro.quiz_service.infrastructure.kafka.producer.ankiCardReviewed.AnkiCardReviewedEvent;
import com.dmytro.quiz_service.infrastructure.kafka.producer.ankiCardReviewed.AnkiReviewProducer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReviewAnkiInteractor implements ReviewAnkiUseCase {

    private static final Logger log = LoggerFactory.getLogger(ReviewAnkiInteractor.class);

    private final AnkiCardPort ankiCardPort;
    private final AnkiService ankiService;
    private final AnkiReviewProducer producer;

    @Override
    public AnkiCard review(UUID cardId, int rating, String userEmail) {
        AnkiCard card = ankiCardPort.findById(cardId)
                .orElseThrow(() -> new AnkiCardNotFoundException(cardId.toString()));

        if (!card.getUserEmail().equals(userEmail)) {
            throw new AccessDeniedException("Not your card");
        }

        AnkiCard updatedCard = ankiService.applyFsrs(card, rating);
        AnkiCard saved = ankiCardPort.save(updatedCard);

        try {
            producer.sendReviewEvent(new AnkiCardReviewedEvent(
                    saved.getId(),
                    saved.getWordId(),
                    saved.getWord(),
                    saved.getUserEmail(),
                    rating,
                    saved.getState().name()
            ));
        } catch (Exception e) {
            log.error("Failed to publish AnkiCardReviewedEvent for card {}: {}", saved.getId(), e.getMessage(), e);
        }

        return saved;
    }
}
