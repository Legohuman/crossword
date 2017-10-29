package ru.dlevin.cross.utils;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.word.Word;


public class WordUtils {
    public static final String validWordPattern = "^[абвгдежзийклмнопрстуфхцчшщъыьэюя]+$";

    public static boolean isValidRussianWord(@NotNull String word) {
        return word.matches(validWordPattern);
    }

    public static void checkValidRussianWord(@NotNull String word) {
        Validate.argument(() -> !word.isEmpty(), "Word should not be empty");
        Validate.argument(() -> isValidRussianWord(word), "Invalid word " + word + ". Word is expected to match pattern " + validWordPattern);
    }

    public static int relativeCharIndex(char c) {
        return c - 'а';
    }

    @NotNull
    public static String preprocessWordText(@NotNull String text) {
        String trimmed = text.trim();
        Validate.argument(() -> !trimmed.isEmpty(), "Invalid word text, non-blank value is expected");
        Validate.argument(() -> trimmed.length() >= Word.minWordLength, "Invalid word text, expected value should not be shorter than " + Word.minWordLength + " characters");
        return trimmed;
    }
}
