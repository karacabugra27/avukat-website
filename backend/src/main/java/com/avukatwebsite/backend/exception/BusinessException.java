package com.avukatwebsite.backend.exception;

public class BusinessException extends BaseException {

    public BusinessException(ErrorType errorType) {
        super(errorType);
    }

    public BusinessException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
