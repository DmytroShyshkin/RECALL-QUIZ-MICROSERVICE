package com.dmytro.quiz_service.infrastructure.kafka.consumer.wordUpserted;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dmytro.quiz_service.domain.model.WordSnapshot;

@Mapper(componentModel = "spring")
public interface WordUpsertedEventMapper {

    @Mapping(target = "ownerEmail", source = "userEmail")
    WordSnapshot toDomain(WordUpsertedEvent event);

    WordSnapshot.TranslationSnapshot toTranslationSnapshot(WordUpsertedEvent.TranslationPayload payload);

    List<WordSnapshot.TranslationSnapshot> toTranslationSnapshotList(List<WordUpsertedEvent.TranslationPayload> payloads);
}
