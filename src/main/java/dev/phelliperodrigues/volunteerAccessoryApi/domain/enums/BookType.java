package dev.phelliperodrigues.volunteerAccessoryApi.domain.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum BookType {
    ADMINISTRATIVO, MANUTENCAO;

    private static final List<BookType> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static BookType random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
