package com.dmytro.quiz_service.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class WordLearningProgress {
    private UUID id;
    private UUID userId;
    private UUID wordId;
    private String word;
    private int correctAnswers;
    private int wrongAnswers;
    private double masteryScore;
    private LocalDateTime lastReviewed;
}
