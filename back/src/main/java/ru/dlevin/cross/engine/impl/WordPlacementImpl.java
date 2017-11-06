package ru.dlevin.cross.engine.impl;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.WordPlacement;
import ru.dlevin.cross.engine.api.board.WordContainer;
import ru.dlevin.cross.engine.api.word.Word;

public class WordPlacementImpl implements WordPlacement {
    @NotNull
    private final Word word;
    @NotNull
    private final WordContainer wordContainer;

    public WordPlacementImpl(@NotNull Word word, @NotNull WordContainer wordContainer) {
        this.word = word;
        this.wordContainer = wordContainer;
    }

    @NotNull
    @Override
    public Word getWord() {
        return word;
    }

    @NotNull
    @Override
    public WordContainer getContainer() {
        return wordContainer;
    }

    @Override
    public String toString() {
        return "container: " + wordContainer + ", word: " + word;
    }
}
