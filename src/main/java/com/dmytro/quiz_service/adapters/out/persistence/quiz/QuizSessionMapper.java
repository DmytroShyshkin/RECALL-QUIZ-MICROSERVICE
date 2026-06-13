package com.dmytro.quiz_service.adapters.out.persistence.quiz;

import com.dmytro.quiz_service.domain.model.QuizQuestion;
import com.dmytro.quiz_service.domain.model.QuizSession;
import com.dmytro.quiz_service.infrastructure.persistence.quiz.QuizQuestionDocument;
import com.dmytro.quiz_service.infrastructure.persistence.quiz.QuizSessionDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuizSessionMapper {
    QuizSessionDocument toDocument(QuizSession session);
    QuizSession toDomain(QuizSessionDocument document);
    QuizQuestionDocument toDocument(QuizQuestion question);
    QuizQuestion toDomain(QuizQuestionDocument document);
}
