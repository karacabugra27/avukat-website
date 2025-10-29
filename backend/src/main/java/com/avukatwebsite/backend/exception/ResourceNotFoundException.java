package com.avukatwebsite.backend.exception;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(ErrorType errorType) {
        super(errorType);
    }

    public ResourceNotFoundException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
