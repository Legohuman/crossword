package ru.dlevin.cross.engine.impl;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.CrosswordCreationContext;
import ru.dlevin.cross.engine.api.board.CrosswordBoard;
import ru.dlevin.cross.engine.api.word.dict.ReadOnlyWordDictionary;

public class CrosswordCreationContextImpl implements CrosswordCreationContext {
    @NotNull
    private final CrosswordBoard board;
    @NotNull
    private final ReadOnlyWordDictionary dictionary;

    public CrosswordCreationContextImpl(@NotNull CrosswordBoard board, @NotNull ReadOnlyWordDictionary dictionary) {
        this.board = board;
        this.dictionary = dictionary;
    }

    @NotNull
    @Override
    public CrosswordBoard getBoard() {
        return board;
    }

    @NotNull
    @Override
    public ReadOnlyWordDictionary getDictionary() {
        return dictionary;
    }
}
