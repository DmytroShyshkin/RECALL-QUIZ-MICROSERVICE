package com.dmytro.quiz_service.domain.ports.in;

import com.dmytro.quiz_service.domain.model.AnkiCard;

import java.util.Optional;

public interface NextAnkiCard {
    Optional<AnkiCard> nextAnkiCard(String userEmail);
}
