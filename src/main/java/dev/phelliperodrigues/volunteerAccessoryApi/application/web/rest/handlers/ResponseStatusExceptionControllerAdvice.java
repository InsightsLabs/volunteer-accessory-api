package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.LogUtils.logError;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ResponseStatusExceptionControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(ResponseStatusException ex) {
        logError(log, ex);

        return ResponseEntity.status(ex.getStatusCode()).body(new ApiError(
                new Date(),
                ex.getBody().getTitle(),
                ex.getBody().getStatus(),
                ex.getBody().getDetail(),
                ex.getMessage(),
                ex.getClass().getSimpleName()
        ));
    }
}
