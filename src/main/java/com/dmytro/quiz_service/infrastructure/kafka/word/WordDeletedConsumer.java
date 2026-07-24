package com.dmytro.quiz_service.infrastructure.kafka.word;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.in.DeleteAnkiCardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WordDeletedConsumer {

    private final DeleteAnkiCardUseCase deleteAnkiCardUseCase;

    @KafkaListener(topics="anki.card.reviewed", groupId="microservice")
    public Optional<AnkiCard> handlerDeleteWord(WordDeletedEvent event){
        return deleteAnkiCardUseCase.deleteAnkiCard(event.wordId(), event.userEmail());
    }
}
