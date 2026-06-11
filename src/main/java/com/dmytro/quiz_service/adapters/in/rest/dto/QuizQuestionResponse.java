package com.dmytro.quiz_service.adapters.in.rest.dto;

import java.util.List;
import java.util.UUID;

public record QuizQuestionResponse(
        UUID wordId
        , String question
        , List<String> options
        , List<String> language
) {
}
