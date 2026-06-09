package com.dmytro.quiz_service.adapters.in;

import com.dmytro.quiz_service.domain.ports.out.WordsProviderPort;
import com.dmytro.quiz_service.domain.ports.out.dto.TranslationDTO;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WordsClientAdapter implements WordsProviderPort {

    private final WebClient webClient;

    @Value("${recall.api.url}")
    private String recallApiUrl;

    @Override
    public List<WordsDTO> getWordsByUser(String jwt, String language) {

        String token = jwt.startsWith("Bearer ")
                ? jwt
                : "Bearer " + jwt;

        PageResponse response = webClient
                .get()
                .uri(recallApiUrl + "/words/user?pageNo=0&pageSize=1000&language=" + language)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(PageResponse.class)
                .block();

        if (response == null || response.content() == null) {
            return List.of();
        }

        return response.content().stream()
                .map(this::toWordDTO)
                .toList();
    }

    private WordsDTO toWordDTO(WordsApiResponse word) {
        List<TranslationDTO> translations = word.translations() == null
                ? List.of()
                : word.translations().stream()
                  .map(t -> new TranslationDTO(
                          t.targetLanguage(),
                          t.translatedWord(),
                          t.description()
                  ))
                  .toList();

        return new WordsDTO(
                word.id(),
                word.sourceLanguage(),
                word.originalWord(),
                translations
        );
    }

    private record PageResponse(
            List<WordsApiResponse> content,
            int pageNo,
            int pageSize,
            int totalPages,
            long totalElements,
            boolean last
    ) {}

    private record WordsApiResponse(
            UUID id,
            String sourceLanguage,
            String originalWord,
            List<TranslationApiResponse> translations
    ) {}

    private record TranslationApiResponse(
            UUID id,
            String targetLanguage,
            String translatedWord,
            String description
    ) {}
}
