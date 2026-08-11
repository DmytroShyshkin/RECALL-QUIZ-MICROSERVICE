package com.dmytro.quiz_service.domain.exception;

public class QuizAlreadyCompletedException extends ConflictException {
    public QuizAlreadyCompletedException(String sessionId) {
        super("Quiz session already completed: " + sessionId);
    }
}
