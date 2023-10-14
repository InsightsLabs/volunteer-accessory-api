package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

import static dev.phelliperodrigues.volunteerAccessoryApi.utils.LogUtils.logError;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class DataIntegrityViolationExceptionControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleNullPointerException(DataIntegrityViolationException ex) {
        logError(log, ex);
        if (ex.getMessage().contains("duplicate key value violates unique constraint")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(
                    new Date(),
                    BAD_REQUEST.name(),
                    BAD_REQUEST.value(),
                    "Desculpe, mas encontramos um problema ao processar sua solicitação. " +
                            "Algumas das informaçoes como nome pode estar duplicada e ja existir cadastrado",
                    ex.getMessage(),
                    ex.getClass().getSimpleName()
            ));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(
                new Date(),
                BAD_REQUEST.name(),
                BAD_REQUEST.value(),
                "Desculpe, mas encontramos um problema ao processar sua solicitação. " +
                        "Isso pode ser devido a um erro interno no servidor. Por favor, " +
                        "entre em contato com o suporte técnico para obter assistência adicional.",
                ex.getMessage(),
                ex.getClass().getSimpleName()
        ));
    }
}
