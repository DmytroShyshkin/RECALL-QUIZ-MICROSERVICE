package com.dmytro.quiz_service.domain.model;

public enum CardState {
    NEW,        // new card, not yet studied
    LEARNING,   // currently studying
    REVIEW,     // reviewing
    RELEARNING  // forgotten, studying again
}
