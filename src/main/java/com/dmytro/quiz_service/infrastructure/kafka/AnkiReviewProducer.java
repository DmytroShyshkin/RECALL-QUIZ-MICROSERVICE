package com.dmytro.quiz_service.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnkiReviewProducer {

    private static final String TOPIC = "anki.card.reviewed";
    private final KafkaTemplate<String, AnkiCardReviewedEvent> kafkaTemplate;

    public void sendReviewEvent(AnkiCardReviewedEvent event) {
        kafkaTemplate.send(TOPIC, event.cardId().toString(), event);
    }
}