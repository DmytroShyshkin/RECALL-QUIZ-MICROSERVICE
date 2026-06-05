package com.dmytro.quiz_service.domain.ports.out.dto;

import java.util.List;

public record WordsDTO(
        String sourceLanguage
        , String originalWord
        , List<TranslationDTO> translations
        ) {
}
