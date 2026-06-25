package com.dmytro.quiz_service.adapters.out.dto;


import com.dmytro.quiz_service.domain.ports.out.dto.TranslationDTO;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WordsApiMapper {
    @Mapping(source = "id", target = "wordId")
    WordsDTO toWordDTO(WordsApiResponse response);

    TranslationDTO toTranslationDTO(TranslationApiResponse response);

    @IterableMapping(elementTargetType = WordsDTO.class)
    List<WordsDTO> toWordDTOList(List<WordsApiResponse> responses);
}
