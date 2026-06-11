package com.dmytro.quiz_service.adapters.in.rest.dto;

import java.util.List;

public record StartQuizRequest(
        List<String> languages
        , int wordCount
) {
}
