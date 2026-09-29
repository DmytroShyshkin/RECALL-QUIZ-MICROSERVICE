package com.dmytro.quiz_service.adapters.out.persistence.words;

import java.util.List;

import com.dmytro.quiz_service.domain.model.WordSnapshot;
import com.dmytro.quiz_service.domain.ports.out.dto.TranslationDTO;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WordSnapshotToWordsDtoMapper {

    WordsDTO toWordsDto(WordSnapshot source);

    @Mapping(target = "description", ignore = true)
    TranslationDTO toTranslationDto(WordSnapshot.TranslationSnapshot source);

    List<WordsDTO> toWordsDtoList(List<WordSnapshot> sources);
}
