package com.dmytro.quiz_service.adapters.out.persistence.words;

import org.mapstruct.Mapper;

import com.dmytro.quiz_service.domain.model.WordSnapshot;
import com.dmytro.quiz_service.infrastructure.persistence.words.WordSnapshotDocument;

@Mapper(componentModel = "spring")
public interface WordSnapshotMapper {
    WordSnapshotDocument toDocument(WordSnapshot snapshot);
    WordSnapshot toDomain(WordSnapshotDocument document);
}
