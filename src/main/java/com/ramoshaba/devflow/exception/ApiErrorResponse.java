package com.ramoshaba.devflow.exception;

import java.time.Instant;

/**
 * Represents the standard error response returned by the MCP DevFlow API.
 *
 * @param timestamp the time at which the error occurred
 * @param status the HTTP status code
 * @param error the HTTP error description
 * @param message the user-friendly error message
 * @param path the request path where the error occurred
 *
 * @author Itumeleng Ramoshaba
 */
public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {
}