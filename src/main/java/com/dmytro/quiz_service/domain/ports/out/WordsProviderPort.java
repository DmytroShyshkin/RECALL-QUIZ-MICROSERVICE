package com.dmytro.quiz_service.domain.ports.out;

import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;

import java.util.List;

public interface WordsProviderPort {
    List<WordsDTO> getWordsByUser(String jwt, String language);
}
