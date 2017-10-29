package ru.dlevin.cross.api.word;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface WordPattern {

    char wildcard = '*';

    int getLength();

    boolean matchWord(@NotNull Word word);

    @NotNull
    WordPattern withLetter(@NotNull WordPatternLetter letter);

    List<WordPatternLetter> getKnownLetters();

    boolean allLettersKnown();

    Word toWord();
}
