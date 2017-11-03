package ru.dlevin.cross.utils;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public class Validate {

    public static void argument(@NotNull Supplier<Boolean> validation, @NotNull String message) {
        validate(validation, message, IllegalArgumentException::new);
    }

    public static void state(@NotNull Supplier<Boolean> validation, @NotNull String message) {
        validate(validation, message, IllegalStateException::new);
    }

    private static <T extends RuntimeException> void validate(@NotNull Supplier<Boolean> validation, @NotNull String message, @NotNull Function<String, T> exceptionFactory) {
        Boolean result = validation.get();
        if (result != null && !result) {
            throw exceptionFactory.apply(message);
        }
    }
}
