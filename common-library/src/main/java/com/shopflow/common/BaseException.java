package com.shopflow.common;

/**
 * Base exception class for all custom exceptions in the ShopFlow project
 */
public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    private final String message;

    public BaseException(String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return message;
    }
}

