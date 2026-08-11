package com.dmytro.quiz_service.domain.exception;

public class AnkiCardNotFoundException extends NotFoundException {
    public AnkiCardNotFoundException(String cardId) {
        super("Anki card not found: " + cardId);
    }
}
