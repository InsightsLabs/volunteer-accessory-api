package dev.phelliperodrigues.volunteerAccessoryApi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Exceptions {

    public static ResponseStatusException invalidIdException() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato do ID inválido");
    }

    public static ResponseStatusException notFoundException(String message) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }
}
