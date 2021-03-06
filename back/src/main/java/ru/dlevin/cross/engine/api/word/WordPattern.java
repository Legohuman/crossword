package ru.dlevin.cross.engine.api.word;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface WordPattern {

    char wildcard = '*';

    int getLength();

    boolean matchWord(@NotNull Word word);

    @NotNull
    WordPattern withLetter(@NotNull WordPatternLetter letter);

    @NotNull
    List<WordPatternLetter> getKnownLetters();

    boolean allLettersKnown();

    @NotNull
    Word toWord();
}
