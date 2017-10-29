package ru.dlevin.cross.api;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.api.board.ContainerOrientation;
import ru.dlevin.cross.api.board.CrosswordBoard;

import java.util.SortedSet;

public interface Crossword {

    @NotNull
    CrosswordBoard getBoard();

    boolean isFinished();

    @NotNull
    SortedSet<WordPlacement> getPlacements();

    @NotNull
    SortedSet<WordPlacement> getPlacements(@NotNull ContainerOrientation orientation);

}
