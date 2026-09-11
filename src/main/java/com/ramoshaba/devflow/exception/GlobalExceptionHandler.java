package com.ramoshaba.devflow.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * Handles unexpected exceptions across the MCP DevFlow REST API
 * and converts them into consistent HTTP error responses.
 *
 * @author Itumeleng Ramoshaba
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles unexpected application exceptions and returns
     * a standard HTTP 500 Internal Server Error response.
     *
     * @param exception the exception that occurred
     * @param request the HTTP request that caused the exception
     * @return a standardized API error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
            Exception exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                "Internal server error",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}