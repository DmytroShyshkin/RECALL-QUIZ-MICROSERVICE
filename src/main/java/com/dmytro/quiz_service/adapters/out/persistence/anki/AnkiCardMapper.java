package com.dmytro.quiz_service.adapters.out.persistence.anki;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.infrastructure.persistence.anki.AnkiCardDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnkiCardMapper {
    AnkiCardDocument toDocument(AnkiCard ankiCard);
    AnkiCard toDomain(AnkiCardDocument ankiCardDocument);
}
