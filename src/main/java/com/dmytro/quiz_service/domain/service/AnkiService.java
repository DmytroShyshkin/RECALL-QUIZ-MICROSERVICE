package com.dmytro.quiz_service.domain.service;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.in.InitiateAnkiCard;
import com.dmytro.quiz_service.domain.ports.in.NextAnkiCard;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import com.dmytro.quiz_service.domain.ports.out.WordsProviderPort;
import com.dmytro.quiz_service.domain.ports.out.dto.TranslationDTO;
import com.dmytro.quiz_service.infrastructure.config.JwtUtil;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class AnkiService implements InitiateAnkiCard, NextAnkiCard {

    private final AnkiCardPort ankiCardPort;
    private final WordsProviderPort wordsProvider;
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
                                  .map(TranslationDTO::targetLanguage)
                                  .orElse(""))
                        .repetitions(0)
                        .intervalDays(1)
                        .easeFactor(2.5)
                        .nextReviewAt(LocalDateTime.now())
                        .build())
                .toList();

        return cards.stream()
                .map(ankiCardPort::save)
                .toList();
    }

    @Override
    public AnkiCard nextAnkiCard(String userEmail) {
        return ankiCardPort.findDueCards(userEmail, LocalDateTime.now())
                .stream()
                .min(Comparator.comparing(AnkiCard::getNextReviewAt))
                .orElseThrow(() -> new IllegalStateException("No cards due for review"));
    }

    private String extractEmail(String jwt) {
        String token = jwt.startsWith("Bearer ") ? jwt.substring(7) : jwt;
        return jwtUtil.extractEmail(token);
    }
}
