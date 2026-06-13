package com.dmytro.quiz_service.adapters.out.dto;


import com.dmytro.quiz_service.domain.ports.out.dto.TranslationDTO;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WordsApiMapper {
    WordsDTO toWordDTO(WordsApiResponse response);
    TranslationDTO toTranslationDTO(TranslationApiResponse response);
    List<WordsDTO> toWordDTOList(List<WordsApiResponse> responses);
}
