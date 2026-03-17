package com.shopflow.common;

/**
 * Exception thrown when business logic validation fails
 * Example: Duplicate email, invalid state transition, etc.
 */
public class BusinessException extends BaseException {
    
    public BusinessException(String message, String errorCode) {
        super(message, errorCode);
    }

    public BusinessException(String message) {
        super(message, "BUS-001");
    }
}

