package com.dmytro.quiz_service.adapters.out.dto;

import java.util.UUID;

public record TranslationApiResponse(
        UUID id,
        String targetLanguage,
        String translatedWord,
        String description
) {}
