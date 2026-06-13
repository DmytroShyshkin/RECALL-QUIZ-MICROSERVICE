package com.dmytro.quiz_service.adapters.out.persistence.anki;

import com.dmytro.quiz_service.domain.model.AnkiCard;
import com.dmytro.quiz_service.domain.ports.out.AnkiCardPort;
import com.dmytro.quiz_service.infrastructure.persistence.anki.JpaAnkiCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AnkiCardRepositoryAdapter implements AnkiCardPort {

    private final JpaAnkiCardRepository repository;
    private final AnkiCardMapper mapper;

    @Override
    public AnkiCard save(AnkiCard card) {
        return mapper.toDomain(
                repository.save(
                                mapper.toDocument(card)
                        )
        );
    }

    @Override
    public Optional<AnkiCard> findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<AnkiCard> findDueCards(String userEmail, LocalDateTime before) {
        return repository
                .findByUserEmailAndNextReviewAtBefore(userEmail, before)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
