package dev.phelliperodrigues.volunteerAccessoryApi.utils;

public class UUID {

    public static java.util.UUID fromStrg(String id) {
        try {

            return id != null ? java.util.UUID.fromString(id) : null;
        } catch (IllegalArgumentException ex) {
            throw Exceptions.invalidIdException(id);
        }
    }
}
