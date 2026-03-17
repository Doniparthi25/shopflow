package com.shopflow.common;

/**
 * Exception thrown when a requested resource is not found
 * Example: User not found, Order not found, etc.
 */
public class ResourceNotFoundException extends BaseException {
    
    public ResourceNotFoundException(String message) {
        super(message, "RES-001");
    }

    public ResourceNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }
}

