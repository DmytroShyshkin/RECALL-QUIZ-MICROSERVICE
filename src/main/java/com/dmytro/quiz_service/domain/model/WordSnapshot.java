package com.dmytro.quiz_service.domain.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordSnapshot {
    private UUID wordId;
    private String ownerEmail;
    private String sourceLanguage;
    private String originalWord;
    private List<TranslationSnapshot> translations;

    public record TranslationSnapshot(
        String targetLanguage
        , String translatedWord
    ) {
    }
}
