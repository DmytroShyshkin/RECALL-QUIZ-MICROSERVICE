package com.dmytro.quiz_service.infrastructure.persistence.quiz;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "quiz_sessions")
public class QuizSessionDocument {

    @Id
    private UUID sessionId;
    private UUID userId;
    private String language;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private List<QuizQuestionDocument> questions;
    private int currentIndex;
    private int score;
    private boolean completed;
}
