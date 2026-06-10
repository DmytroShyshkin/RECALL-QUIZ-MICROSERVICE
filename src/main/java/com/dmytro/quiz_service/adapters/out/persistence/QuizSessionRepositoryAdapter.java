package com.dmytro.quiz_service.adapters.out.persistence;

import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.infrastructure.persistence.JpaQuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuizSessionRepositoryAdapter {
    private final JpaQuizSessionRepository repository;
    private final QuizSessionMapper mapper;

    @Override
    public QuizSession save(QuizSession session) {
        return mapper.toDomain(
                repository.save(
                        mapper.toDocument(session)
                )
        );
    }

    @Override
    public Optional<QuizSession> findSessionById(UUID sessionId) {
        return repository.findBySessionId(sessionId)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID sessionId) {
        repository.findBySessionId(sessionId)
                .ifPresent(doc -> repository.deleteById(doc.getId()));
    }
}
