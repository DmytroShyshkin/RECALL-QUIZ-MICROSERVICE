package com.dmytro.quiz_service.domain.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.model.CardState;

@Component
public class AnkiService {

    // classic Anki-style defaults — tune to taste
    private static final int[] LEARNING_STEPS_MINUTES = {1, 10};
    private static final int[] RELEARNING_STEPS_MINUTES = {10};

    public AnkiCard applyFsrs(AnkiCard card, int rating) {
        return switch (card.getState()) {
            case NEW, LEARNING -> applyLearningStep(card, rating, LEARNING_STEPS_MINUTES);
            case RELEARNING -> applyLearningStep(card, rating, RELEARNING_STEPS_MINUTES);
            case REVIEW -> applyReview(card, rating);
        };
    }

    // Card moves through a fixed queue of short steps — used both for initial
    // learning and for relearning after a lapse; the step set is passed in.
    private AnkiCard applyLearningStep(AnkiCard card, int rating, int[] steps) {
        boolean wasRelearning = card.getState() == CardState.RELEARNING;
        int currentStep = card.getLearningStep();

        int nextStep;
        boolean graduated;

        switch (rating) {
            case 1 -> { // Again
                nextStep = 0;
                graduated = false;
            }
            case 2 -> { // Hard
                nextStep = currentStep;
                graduated = false;
            }
            case 4 -> { // Easy
                nextStep = 0;
                graduated = true;
            }
            default -> { // 3 = Good
                nextStep = currentStep + 1;
                graduated = nextStep >= steps.length;
            }
        }

        if (graduated) {
            // Reuses updateStability()'s own branching: graduating from NEW/LEARNING
            // hits its "starting" values, graduating from RELEARNING hits the main
            // FSRS formula based on the card's existing history.
            double difficulty = updateDifficulty(card.getDifficulty(), rating);
            card.setDifficulty(difficulty);
            double stability = updateStability(card, rating);
            card.setStability(stability);
            if (!wasRelearning) {
                card.setRetrievability(1.0);
            }

            card.setState(CardState.REVIEW);
            card.setLearningStep(0);

            int intervalDays = calculateInterval(stability);
            card.setNextReviewAt(LocalDateTime.now().plusDays(intervalDays));
        } else {
            card.setState(wasRelearning ? CardState.RELEARNING : CardState.LEARNING);
            card.setLearningStep(nextStep);
            card.setNextReviewAt(LocalDateTime.now().plusMinutes(steps[nextStep]));
        }

        card.setLastReviewAt(LocalDateTime.now());
        card.setRepetitions(card.getRepetitions() + 1);
        return card;
    }

    // An already-graduated (REVIEW) card gets its next rating.
    private AnkiCard applyReview(AnkiCard card, int rating) {
        if (rating == 1) {
            // Lapse — drop back into RELEARNING and run it through the same short
            // step queue as initial learning, instead of shrinking stability in place.
            card.setLapses(card.getLapses() + 1);
            card.setState(CardState.RELEARNING);
            card.setLearningStep(0);
            return applyLearningStep(card, rating, RELEARNING_STEPS_MINUTES);
        }

        card.setRetrievability(calculateRetrievability(card));
        card.setDifficulty(updateDifficulty(card.getDifficulty(), rating));
        card.setStability(updateStability(card, rating));

        int interval = calculateInterval(card.getStability());

        card.setRepetitions(card.getRepetitions() + 1);
        card.setLastReviewAt(LocalDateTime.now());
        card.setNextReviewAt(LocalDateTime.now().plusDays(interval));

        return card;
    }

    private double calculateRetrievability(AnkiCard card) {
        if (card.getLastReviewAt() == null) return 1.0;
        long daysSince = ChronoUnit.DAYS.between(card.getLastReviewAt(), LocalDateTime.now());
        if (daysSince == 0) return 1.0;
        if (card.getStability() <= 0) return 0.0;
        return Math.pow(0.9, (double) daysSince / card.getStability());
    }

    private double updateDifficulty(double difficulty, int rating) {
        double step = 0.15;
        double delta = -(rating - 3) * step;

        double newDifficulty = difficulty + delta;
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