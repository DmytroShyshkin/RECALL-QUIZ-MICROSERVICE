package com.dmytro.quiz_service.adapters.out.dto;

import com.dmytro.quiz_service.adapters.out.words.WordsClientAdapter;

import java.util.List;
import java.util.UUID;

public record WordsApiResponse(
        UUID id,
        String sourceLanguage,
        String originalWord,
        List<TranslationApiResponse> translations
) {
}
