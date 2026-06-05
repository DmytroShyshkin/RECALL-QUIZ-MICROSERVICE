package com.dmytro.quiz_service.domain.model;

import java.util.List;
import java.util.UUID;

public class QuizQuestion {
    private UUID wordId;
    private String question;
    private List<String> options;
    private String correctAnswer;
    private String userAnswer;
    private boolean answeredCorrectly;
}
