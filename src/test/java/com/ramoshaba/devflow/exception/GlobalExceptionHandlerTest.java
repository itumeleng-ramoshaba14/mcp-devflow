package com.ramoshaba.devflow.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the behavior of {@link GlobalExceptionHandler}.
 *
 * @author Itumeleng Ramoshaba
 */
class GlobalExceptionHandlerTest {

    /**
     * Verifies that an unexpected exception is converted into
     * a standard HTTP 500 Internal Server Error response.
     */
    @Test
    void unexpectedExceptionReturnsInternalServerError() {

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/api/example");

        ResponseEntity<ApiErrorResponse> response =
                handler.handleUnexpectedException(new RuntimeException("Unexpected failure"), request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().message());
        assertEquals("/api/example", response.getBody().path());
    }
}