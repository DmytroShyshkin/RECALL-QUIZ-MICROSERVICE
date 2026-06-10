package com.dmytro.quiz_service.infrastructure.persistence.anki;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnkiCardDocument {
    private UUID id;
    private UUID wordId;
    private String userEmail;
    private String word;
    private String translation;
    private int repetitions;
    private int intervalDays;
    private double easeFactor;
    private LocalDateTime nextReviewAt;
}
