package ru.dlevin.cross.utils;

import java.util.function.Function;
import java.util.function.Supplier;

public class Validate {

    public static void argument(Supplier<Boolean> validation, String message) {
        validate(validation, message, IllegalArgumentException::new);
    }

    public static void state(Supplier<Boolean> validation, String message) {
        validate(validation, message, IllegalStateException::new);
    }

    private static <T extends RuntimeException> void validate(Supplier<Boolean> validation, String message, Function<String, T> exceptionFactory) {
        Boolean result = validation.get();
        if (result != null && !result) {
            throw exceptionFactory.apply(message);
        }
    }
}
