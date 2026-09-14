package com.dmytro.quiz_service.infrastructure.persistence.words;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dmytro.quiz_service.domain.model.WordSnapshot.TranslationSnapshot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "wordSnapshotsDocument")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordSnapshotDocument {
    @Id 
    private UUID wordId;
    private String ownerEmail;
    private String sourceLanguage;
    private String originalWord;
    private List<TranslationSnapshot> translations;
}
