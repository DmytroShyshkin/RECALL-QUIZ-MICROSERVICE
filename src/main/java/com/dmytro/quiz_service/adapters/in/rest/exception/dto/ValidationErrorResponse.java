package com.dmytro.quiz_service.adapters.in.rest.exception.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ValidationErrorResponse extends ErrorResponse {

    private final List<FieldValidationError> errors;

    public ValidationErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path,
            List<FieldValidationError> errors
    ) {
        super(timestamp, status, error, message, path);
        this.errors = errors;
    }
}
