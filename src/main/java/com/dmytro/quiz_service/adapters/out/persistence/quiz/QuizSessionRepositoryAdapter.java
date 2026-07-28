package com.dmytro.quiz_service.adapters.out.persistence.quiz;

import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.domain.ports.out.QuizRepositoryPort;
import com.dmytro.quiz_service.infrastructure.persistence.quiz.JpaQuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuizSessionRepositoryAdapter implements QuizRepositoryPort {
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
        return repository.findById(sessionId)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID sessionId) {
        repository.deleteById(sessionId);
    }

    @Override
    public void deleteAllByUserId(UUID userId) {
        repository.deleteAllByUserId(userId);
    }
}
