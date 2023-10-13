package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers;

import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.LogUtils.logError;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice

public class CommonExceptionHandler {


    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logError(log, ex);
        return ResponseEntity.badRequest().body(new ApiError(
                new Date(),
                BAD_REQUEST.name(),
                BAD_REQUEST.value(),
                ex.getMessage(),
                ex.getCause().getMessage(),
                ex.getClass().getSimpleName()
        ));
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ApiError> unexpectedTypeException(UnexpectedTypeException ex) {
        logError(log, ex);
        return ResponseEntity.badRequest().body(new ApiError(
                new Date(),
                INTERNAL_SERVER_ERROR.name(),
                INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                ex.getCause().getMessage(),
                ex.getClass().getSimpleName()
        ));
    }
}
