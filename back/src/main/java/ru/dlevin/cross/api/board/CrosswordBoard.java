package ru.dlevin.cross.api.board;

import org.jetbrains.annotations.NotNull;

import java.util.SortedSet;

public interface CrosswordBoard {

    @NotNull
    SortedSet<WordContainer> getContainers();

    @NotNull
    BoardDimensions getDimensions();
}
