package com.dmytro.quiz_service.domain.exception;

public class QuizSessionNotFoundException extends NotFoundException {
    public QuizSessionNotFoundException(String sessionId) {
        super("Quiz session not found: " + sessionId);
    }
}
