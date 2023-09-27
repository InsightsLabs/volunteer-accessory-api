package dev.phelliperodrigues.volunteerAccessoryApi.utils;

import com.github.javafaker.Faker;

public class FakerUtil {

    // Create singleton instance of Faker
    private static final Faker faker = Faker.instance();

    // Create private constructor
    private FakerUtil() {
    }

    // Create a static method to get instance.
    public static Faker getInstance() {
        return faker;
    }


}
