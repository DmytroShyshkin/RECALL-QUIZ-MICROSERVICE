package com.dmytro.quiz_service.application.usercase.anki;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.in.NextAnkiCard;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NextAnkiInteractor implements NextAnkiCard {

    private final AnkiCardPort ankiCardPort;

    @Override
    public Optional<AnkiCard> nextAnkiCard(String userEmail) {
        // Пустой Optional - валидное состояние "сейчас нечего повторять",
        // а не ошибка. Раньше здесь бросался IllegalStateException,
        // из-за чего /anki/next падал с 500, как только очередь пустела.
        return ankiCardPort.findDueCards(userEmail, LocalDateTime.now())
                .stream()
                .min(Comparator.comparing(AnkiCard::getNextReviewAt));
    }
}
