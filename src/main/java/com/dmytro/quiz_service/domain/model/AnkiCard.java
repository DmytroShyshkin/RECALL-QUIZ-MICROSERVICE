package com.dmytro.quiz_service.domain.model;

import java.util.UUID;

public class AnkiCard {
    private UUID id;
    private UUID wordId;
    private UUID userId;
    private int repetitions;
    private int intervalDays;
    private double easeFactor;
}
