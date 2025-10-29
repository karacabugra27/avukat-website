package com.avukatwebsite.backend.exception;

import com.avukatwebsite.backend.config.TraceIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                      HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fieldErrors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse response = ErrorResponse.from(
                ErrorType.VALIDATION_FAILED,
                ErrorType.VALIDATION_FAILED.getDefaultMessage(),
                request.getRequestURI(),
                fieldErrors,
                currentTraceId()
        );

        return ResponseEntity.status(ErrorType.VALIDATION_FAILED.getHttpStatus()).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException exception,
                                                                   HttpServletRequest request) {
        Map<String, String> violations = new LinkedHashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            violations.putIfAbsent(violation.getPropertyPath().toString(), violation.getMessage());
        }

        ErrorResponse response = ErrorResponse.from(
                ErrorType.VALIDATION_FAILED,
                ErrorType.VALIDATION_FAILED.getDefaultMessage(),
                request.getRequestURI(),
                violations,
                currentTraceId()
        );

        return ResponseEntity.status(ErrorType.VALIDATION_FAILED.getHttpStatus()).body(response);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exception,
                                                             HttpServletRequest request) {
        ErrorType errorType = exception.getErrorType();
        log.warn("Handled business exception: {} - {}", errorType.getCode(), exception.getMessage());

        ErrorResponse response = ErrorResponse.from(
                errorType,
                exception.getMessage(),
                request.getRequestURI(),
                currentTraceId()
        );

        return ResponseEntity.status(errorType.getHttpStatus()).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException exception,
                                                                      HttpServletRequest request) {
        log.warn("Data integrity violation", exception);

        ErrorResponse response = ErrorResponse.from(
                ErrorType.GENERIC_BUSINESS_ERROR,
                "Veri bütünlüğü ihlali",
                request.getRequestURI(),
                currentTraceId()
        );

        return ResponseEntity.status(ErrorType.GENERIC_BUSINESS_ERROR.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exception,
                                                                   HttpServletRequest request) {
        log.error("Unexpected error", exception);

        ErrorResponse response = ErrorResponse.from(
                ErrorType.INTERNAL_SERVER_ERROR,
                ErrorType.INTERNAL_SERVER_ERROR.getDefaultMessage(),
                request.getRequestURI(),
                currentTraceId()
        );

        return ResponseEntity.status(ErrorType.INTERNAL_SERVER_ERROR.getHttpStatus()).body(response);
    }

    private String currentTraceId() {
        return MDC.get(TraceIdFilter.TRACE_ID_KEY);
    }
}
