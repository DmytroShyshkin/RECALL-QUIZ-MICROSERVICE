package com.dmytro.quiz_service.domain.exception;

public class NoWordsAvailableException extends ConflictException {
    public NoWordsAvailableException() {
        super("No words available to start a quiz");
    }
}
