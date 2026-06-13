package com.dmytro.quiz_service.domain.service;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.model.CardState;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class AnkiService {

    public AnkiCard applyFsrs(AnkiCard card, int rating) {
        card.setRetrievability(calculateRetrievability(card));
        card.setDifficulty(updateDifficulty(card.getDifficulty(), rating));
        card.setStability(updateStability(card, rating));

        int interval = calculateInterval(card.getStability());

        card.setState(rating == 1 ? CardState.RELEARNING : CardState.REVIEW);
        card.setRepetitions(rating == 1 ? 0 : card.getRepetitions() + 1);
        card.setLapses(rating == 1 ? card.getLapses() + 1 : card.getLapses());
        card.setLastReviewAt(LocalDateTime.now());
        card.setNextReviewAt(LocalDateTime.now().plusDays(interval));

        return card;
    }

    private double calculateRetrievability(AnkiCard card) {
        if (card.getLastReviewAt() == null) return 1.0;
        long daysSince = ChronoUnit.DAYS.between(card.getLastReviewAt(), LocalDateTime.now());
        return Math.pow(0.9, daysSince / card.getStability());
    }

    private double updateDifficulty(double difficulty, int rating) {
        double newDifficulty = difficulty + 0.1 - (rating - 3) * (0.08 + (rating - 3) * 0.02);
        return Math.min(10.0, Math.max(1.0, newDifficulty));
    }

    private double updateStability(AnkiCard card, int rating) {
        if (card.getState() == CardState.NEW || card.getState() == CardState.LEARNING) {
            return switch (rating) {
                case 1 -> 1.0;
                case 2 -> 2.0;
                case 3 -> 4.0;
                case 4 -> 8.0;
                default -> 1.0;
            };
        }

        if (rating == 1) {
            return Math.max(1.0, card.getStability() * 0.2);
        }

        double hardPenalty = rating == 2 ? 0.8 : 1.0;
        double easyBonus = rating == 4 ? 1.3 : 1.0;

        return card.getStability() * (
                Math.exp(0.9) *
                        (11 - card.getDifficulty()) *
                        Math.pow(card.getStability(), -0.2) *
                        (Math.exp((1 - card.getRetrievability()) * 0.9) - 1) *
                        hardPenalty *
                        easyBonus
        );
    }

    private int calculateInterval(double stability) {
        return (int) Math.max(1, Math.round(stability));
    }
}