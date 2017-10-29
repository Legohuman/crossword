package ru.dlevin.cross.api.word;

import org.jetbrains.annotations.NotNull;

public interface Word extends Comparable<Word> {

    int minWordLength = 2;

    @NotNull
    String getText();

    int getLength();

    int getLetterScore(int letterIndex);
}
