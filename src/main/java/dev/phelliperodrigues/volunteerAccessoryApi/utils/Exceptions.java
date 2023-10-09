package dev.phelliperodrigues.volunteerAccessoryApi.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Exceptions {

    public static ResponseStatusException invalidIdException(String id) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("ID: %s com formato inválido", id));
    }

    public static ResponseStatusException notFoundException(String message) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }
}
