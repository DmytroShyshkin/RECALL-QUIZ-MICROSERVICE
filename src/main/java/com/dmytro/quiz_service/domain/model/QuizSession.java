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
public class QuizSession {
    private UUID sessionId;
    private UUID userId;
    private String language;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private List<QuizQuestion> questions;
    private int currentIndex;
    private int score;
    private List<QuizQuestion> correctAnswer;
    private List<QuizQuestion> wrongAnswer;
    private boolean completed;
}
