package com.dmytro.quiz_service.infrastructure.kafka.consumer.wordDeleted;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.in.DeleteAnkiCardUseCase;
import com.dmytro.quiz_service.domain.ports.out.WordSnapshotPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WordDeletedConsumer {

    private final DeleteAnkiCardUseCase deleteAnkiCardUseCase;
    private final WordSnapshotPort wordSnapshotPort;

    @KafkaListener(
            topics="recall.delete.word"
            , groupId="word-deleted-consumer"
            , containerFactory = "wordDeleteContainerFactory"
    )
    public Optional<AnkiCard> handlerDeleteWord(WordDeletedEvent event){
        wordSnapshotPort.deleteByWordId(event.wordId());
        return deleteAnkiCardUseCase.deleteAnkiCard(event.wordId(), event.userEmail());
    }
}
