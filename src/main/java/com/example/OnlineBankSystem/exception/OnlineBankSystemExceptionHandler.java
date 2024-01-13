package com.example.OnlineBankSystem.exception;

import com.mysql.cj.exceptions.ConnectionIsClosedException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OnlineBankSystemExceptionHandler {

    @ExceptionHandler({ConnectionIsClosedException.class})
    public ResponseEntity<Object> handleConnectionIsClosedException(final ConnectionIsClosedException e,
                                                                    final HttpServletRequest request) {
        log.error("Connection is closed : Exception: " + e + ". Request: " + request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler({CallNotPermittedException.class})
    public ResponseEntity<Object> handleCallNotPermittedException(final CallNotPermittedException e,
                                                                  final HttpServletRequest request) {
        log.error("Call not permitted: Exception: " + e + ". Request: " + request);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE ).build();
    }

    @ExceptionHandler({AccountInactiveException.class})
    public ResponseEntity<Object> handleAccountInactiveException(final AccountInactiveException e,
                                                                 final HttpServletRequest request) {
        log.error("Account inactive exception: " + e + " Request: " + request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR ).build();
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException e,
                                                              final HttpServletRequest request) {
        log.error("Access denied: " + e + " Request: " + request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED ).build();
    }
}
