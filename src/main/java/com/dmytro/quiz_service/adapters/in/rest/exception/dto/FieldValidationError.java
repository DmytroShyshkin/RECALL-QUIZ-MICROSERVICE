package com.dmytro.quiz_service.adapters.in.rest.exception.dto;

public record FieldValidationError(
        String field,
        String message
) {
}
