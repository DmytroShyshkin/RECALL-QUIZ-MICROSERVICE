package com.dmytro.quiz_service.adapters.in.rest.quiz;

import com.dmytro.quiz_service.adapters.in.rest.quiz.dto.QuizQuestionResponse;
import com.dmytro.quiz_service.domain.model.QuizQuestion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizResponseMapper {

    QuizQuestionResponse toQuestionResponse(QuizQuestion question);

    List<QuizQuestionResponse> toQuestionResponseList(List<QuizQuestion> questions);
}