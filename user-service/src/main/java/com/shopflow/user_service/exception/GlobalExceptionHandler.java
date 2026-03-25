package com.shopflow.user_service.exception;

import com.shopflow.common.BusinessException;
import com.shopflow.common.ResourceNotFoundException;
import com.shopflow.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Resource not found: {} — path: {}", ex.getMessage(), request.getRequestURI());
        return ErrorResponse.builder()
                .status(404)
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.warn("Business exception: {} — path: {}", ex.getMessage(), request.getRequestURI());
        return ErrorResponse.builder()
                .status(409)
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

     @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
         List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                 .getFieldErrors()
                 .stream()
                 .map(fe -> ErrorResponse.FieldError.builder()
                         .field(fe.getField())
                         .message(fe.getDefaultMessage())
                         .rejectedValue(fe.getRejectedValue())
                         .build())
                 .collect(Collectors.toList());

         log.debug("Validation failed for {}: {}", request.getRequestURI(), fieldErrors);

         return ErrorResponse.builder()
                 .status(400)
                 .errorCode("VALIDATION_FAILED")
                 .message("Request validation failed")
                 .path(request.getRequestURI())
                 .fieldErrors(fieldErrors)
                 .build();
     }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrity(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        // Log the actual cause for debugging, don't expose to client
        log.warn("Data integrity violation at {}: {}", request.getRequestURI(), ex.getMostSpecificCause().getMessage());

        return ErrorResponse.builder()
                .status(409)
                .errorCode("DATA_CONFLICT")
                .message("A user with this email already exists")
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMalformedJson(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        log.debug("Malformed request body at {}", request.getRequestURI());

        return ErrorResponse.builder()
                .status(400)
                .errorCode("MALFORMED_REQUEST")
                .message("Request body is missing or malformed")
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(
            Exception ex, HttpServletRequest request) {

        // Log at ERROR level with full stack trace — for your debugging
        log.error("Unhandled exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        // NEVER expose the real error message to the client
        return ErrorResponse.builder()
                .status(500)
                .errorCode("INTERNAL_ERROR")
                .message("An unexpected error occurred. Please try again later.")
                .path(request.getRequestURI())
                .build();
    }

}
