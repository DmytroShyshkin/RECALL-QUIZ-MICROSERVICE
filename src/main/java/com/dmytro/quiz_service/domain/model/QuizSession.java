package com.dmytro.quiz_service.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class QuizSession {
    private UUID sessionId;
    private UUID userId;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private List<QuizQuestion> questions;
    private boolean completed;
}
