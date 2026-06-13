package com.dmytro.quiz_service.domain.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestion {
    private UUID wordId;
    private String question;
    private List<String> options;
    private String correctAnswer;
    private String userAnswer;
    private boolean answeredCorrectly;
    private List<String> language;
}
