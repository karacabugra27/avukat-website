package com.avukatwebsite.backend.exception;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        String code,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors,
        Instant timestamp
) {

    public static ErrorResponse from(ErrorType errorType, String message, String path) {
        return from(errorType, message, path, null);
    }

    public static ErrorResponse from(ErrorType errorType, String message, String path, Map<String, String> validationErrors) {
        return new ErrorResponse(
                errorType.getCode(),
                errorType.getHttpStatus().value(),
                errorType.getHttpStatus().getReasonPhrase(),
                message,
                path,
                validationErrors,
                Instant.now()
        );
    }
}
