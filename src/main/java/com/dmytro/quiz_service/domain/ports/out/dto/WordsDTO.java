package com.dmytro.quiz_service.domain.ports.out.dto;

import java.util.List;
import java.util.UUID;

public record WordsDTO(
        UUID wordId
        , String sourceLanguage
        , String originalWord
        , List<TranslationDTO> translations
        ) {
}
