package ru.dlevin.cross.engine.api.word;

public interface WordPatternLetter {

    int getIndex();

    boolean isWildcard();

    char getValue();
}
