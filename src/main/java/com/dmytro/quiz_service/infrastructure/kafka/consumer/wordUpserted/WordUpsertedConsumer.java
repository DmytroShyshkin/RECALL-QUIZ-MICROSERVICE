package com.dmytro.quiz_service.infrastructure.kafka.consumer.wordUpserted;

import com.dmytro.quiz_service.domain.model.WordSnapshot;
import com.dmytro.quiz_service.domain.ports.out.WordSnapshotPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WordUpsertedConsumer {

    private final WordSnapshotPort wordSnapshotPort;
    private final WordUpsertedEventMapper mapper;

    @KafkaListener(
            topics = "recall.word.upserted"
            , groupId = "word-upserted-consumer"
            , containerFactory = "wordUpsertedContainerFactory"
    )
    public void handleWordUpserted(WordUpsertedEvent event) {
        WordSnapshot snapshot = mapper.toDomain(event);
        wordSnapshotPort.upsert(snapshot);
    }
}
