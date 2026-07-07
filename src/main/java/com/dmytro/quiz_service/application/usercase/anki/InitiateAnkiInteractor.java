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

        return wordsProvider.getWordsByUser(jwt, sourceLanguage).stream()
                .filter(word -> word.wordId() != null)
                .map(word -> {
                    List<String> translations = word.translations().stream()
                            .filter(t -> t.targetLanguage().equals(targetLanguage))
                            .map(TranslationDTO::translatedWord)
                            .toList();

                    if (translations.isEmpty()) return null;

                    // If the card already exists, we return it; we do not create a duplicate.
                    return ankiCardPort.findByWordIdAndUserEmail(word.wordId(), email)
                            .orElseGet(() -> ankiCardPort.save(AnkiCard.builder()
                                    .id(UUID.randomUUID())
                                    .wordId(word.wordId())
                                    .userEmail(email)
                                    .word(word.originalWord())
                                    .translations(translations)
                                    .stability(1.0)
                                    .difficulty(5.0)
                                    .retrievability(1.0)
                                    .lapses(0)
                                    .repetitions(0)
                                    .state(CardState.NEW)
                                    .nextReviewAt(LocalDateTime.now())
                                    .build()));
                })
                .filter(card -> card != null)
                .toList();
    }

    private String extractEmail(String jwt) {
        String token = jwt.startsWith("Bearer ") ? jwt.substring(7) : jwt;
        return jwtUtil.extractEmail(token);
    }
}
