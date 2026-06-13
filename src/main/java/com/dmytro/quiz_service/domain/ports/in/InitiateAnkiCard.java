package com.dmytro.quiz_service.domain.ports.in;

import com.dmytro.quiz_service.domain.model.AnkiCard;

import java.util.List;

public interface InitiateAnkiCard {
    List<AnkiCard> initiateAnkiCard(String jwt, String sourceLanguage, String targetLanguage);
}
