package com.dmytro.quiz_service.application.usercase.anki;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.model.CardState;
import com.dmytro.quiz_service.domain.ports.in.InitiateAnkiCard;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import com.dmytro.quiz_service.domain.ports.out.WordsProviderPort;
import com.dmytro.quiz_service.domain.ports.out.dto.TranslationDTO;
import com.dmytro.quiz_service.infrastructure.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitiateAnkiInteractor implements InitiateAnkiCard {

    private final WordsProviderPort wordsProvider;
    private final AnkiCardPort ankiCardPort;
    private final JwtUtil jwtUtil;

    @Override
    public List<AnkiCard> initiateAnkiCard(String jwt, String sourceLanguage, String targetLanguage) {
        String email = extractEmail(jwt);

        List<AnkiCard> cards = wordsProvider.getWordsByUser(jwt, sourceLanguage).stream()
                .map(word -> AnkiCard.builder()
                        .id(UUID.randomUUID())
                        .wordId(word.wordId())
                        .userEmail(email)
                        .word(word.originalWord())
                        .translation(word.translations().isEmpty()
                                ? ""
                                : word.translations().stream()
                                  .filter(t -> t.targetLanguage().equals(targetLanguage))
                                  .findFirst()
                                  .map(TranslationDTO::translatedWord)
                                  .orElse(""))
                        .stability(1.0)
                        .difficulty(5.0)
                        .retrievability(1.0)
                        .lapses(0)
                        .repetitions(0)
                        .state(CardState.NEW)
                        .nextReviewAt(LocalDateTime.now())
                        .build())
                .filter(card -> !card.getTranslation().isEmpty())
                .toList();

        return cards.stream()
                .map(ankiCardPort::save)
                .toList();
    }

    private String extractEmail(String jwt) {
        String token = jwt.startsWith("Bearer ") ? jwt.substring(7) : jwt;
        return jwtUtil.extractEmail(token);
    }
}
