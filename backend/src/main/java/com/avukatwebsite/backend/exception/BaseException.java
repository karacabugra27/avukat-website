package com.avukatwebsite.backend.exception;

public abstract class BaseException extends RuntimeException {

    private final ErrorType errorType;

    protected BaseException(ErrorType errorType) {
        super(errorType.getDefaultMessage());
        this.errorType = errorType;
    }

    protected BaseException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
