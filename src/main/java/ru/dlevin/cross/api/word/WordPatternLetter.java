package ru.dlevin.cross.api.word;

public interface WordPatternLetter {

    int getIndex();

    boolean isWildcard();

    char getValue();
}
