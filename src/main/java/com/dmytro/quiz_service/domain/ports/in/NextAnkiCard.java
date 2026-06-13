package com.dmytro.quiz_service.domain.ports.in;

import com.dmytro.quiz_service.domain.model.AnkiCard;

public interface NextAnkiCard {
    AnkiCard nextAnkiCard(String userEmail);
}
