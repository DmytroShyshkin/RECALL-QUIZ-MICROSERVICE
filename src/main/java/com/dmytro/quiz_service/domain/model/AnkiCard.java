package com.dmytro.quiz_service.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnkiCard {
    private UUID id;
    private UUID wordId;
    private String userEmail;
    private String word;
    private List<String> translations;

    // FSRS
    private double stability;      // how long do you remember (in days)
    private double difficulty;     // how difficult is the word (1.0–10.0)
    private double retrievability; // probability of recall (0.0–1.0)
    private int lapses;            // how many times did you forget it?

    private CardState state;       // NEW, LEARNING, REVIEW, RELEARNING
    // ~FSRS

    private int repetitions;
    private LocalDateTime nextReviewAt;
    private LocalDateTime lastReviewAt;
}
