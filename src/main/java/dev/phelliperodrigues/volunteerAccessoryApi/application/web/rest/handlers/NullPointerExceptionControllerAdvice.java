package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.LogUtils.logError;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class NullPointerExceptionControllerAdvice {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiError> handleNullPointerException(NullPointerException ex) {
        logError(log, ex);
        return ResponseEntity.internalServerError().body(new ApiError(
                new Date(),
                INTERNAL_SERVER_ERROR.name(),
                INTERNAL_SERVER_ERROR.value(),
                "Desculpe, mas encontramos um problema ao processar sua solicitação. " +
                        "Isso pode ser devido a um erro interno no servidor. Por favor, " +
                        "entre em contato com o suporte técnico para obter assistência adicional.",
                ex.getMessage(),
                ex.getClass().getSimpleName()
        ));
    }
}
