package com.dmytro.quiz_service.infrastructure.persistence.anki;

import com.dmytro.quiz_service.domain.model.CardState;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "ankiCardDocument")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnkiCardDocument {
    @Id
    private UUID id;
    private UUID wordId;
    private String userEmail;
    private String word;
    private List<String> translations;

    private double stability;
    private double difficulty;
    private double retrievability;
    private int lapses;

    private CardState state;

    private int repetitions;
    private LocalDateTime nextReviewAt;
    private LocalDateTime lastReviewAt;
}
