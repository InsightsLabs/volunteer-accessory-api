package dev.phelliperodrigues.volunteerAccessoryApi.utils;

public class UUID {

    public static java.util.UUID fromString(String id) {
        return id != null ? java.util.UUID.fromString(id) : null;
    }
}
