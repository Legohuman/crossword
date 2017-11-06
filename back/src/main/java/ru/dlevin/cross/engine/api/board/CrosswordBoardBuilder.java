package ru.dlevin.cross.engine.api.board;

import org.jetbrains.annotations.NotNull;

public interface CrosswordBoardBuilder {
    @NotNull
    CrosswordBoardBuilder addHorizontalContainer(int left, int top, int length);
    @NotNull
    CrosswordBoardBuilder addVerticalContainer(int left, int top, int length);
    @NotNull
    CrosswordBoard build();
}
