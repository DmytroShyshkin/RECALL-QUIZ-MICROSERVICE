package com.dmytro.quiz_service.adapters.out.persistence.words;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dmytro.quiz_service.domain.model.WordSnapshot;
import com.dmytro.quiz_service.domain.ports.out.WordSnapshotPort;
import com.dmytro.quiz_service.domain.ports.out.WordsProviderPort;
import com.dmytro.quiz_service.domain.ports.out.dto.WordsDTO;
import com.dmytro.quiz_service.infrastructure.config.JwtUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WordSnapshotProviderAdapter implements WordsProviderPort {

    private final WordSnapshotPort wordSnapshotPort;
    private final WordSnapshotToWordsDtoMapper mapper;
    private final JwtUtil jwtUtil;

    @Override
    public List<WordsDTO> getWordsByUser(String jwt, String language) {
        
        String email = jwtUtil.extractEmail(jwt);

        if (email == null) {    throw new IllegalArgumentException("Invalid JWT token: email not found");   }

        List<WordSnapshot> snapshots = wordSnapshotPort.findAllByOwnerEmailAndSourceLanguage(email, language);
        return mapper.toWordsDtoList(snapshots);
    }

}
