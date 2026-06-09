package com.dmytro.quiz_service.adapters.in;

import com.dmytro.quiz_service.adapters.out.dto.WordsApiMapper;
import com.dmytro.quiz_service.adapters.out.dto.WordsApiResponse;
import com.dmytro.quiz_service.domain.ports.out.WordsProviderPort;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WordsClientAdapter implements WordsProviderPort {

    private final WebClient webClient;
    private final WordsApiMapper mapper;

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

        return mapper.toWordDTOList(response.content());
    }

    private record PageResponse(
            List<WordsApiResponse> content,
            int pageNo,
            int pageSize,
            int totalPages,
            long totalElements,
            boolean last
    ) {}
}
