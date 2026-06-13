package com.dmytro.quiz_service.adapters.in.rest.anki.dto;

public record InitiateAnkiRequest(
        String sourceLanguage
        , String targetLanguage
) {
}
