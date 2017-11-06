package ru.dlevin.cross.engine.impl.board;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.board.BoardDimensions;
import ru.dlevin.cross.engine.api.board.ContainerCoordinate;
import ru.dlevin.cross.engine.api.board.CrosswordBoard;
import ru.dlevin.cross.engine.api.board.WordContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class CrosswordBoardImpl implements CrosswordBoard {

    private final SortedSet<WordContainer> containers;

    CrosswordBoardImpl(Collection<WordContainer> containers) {
        this.containers = toSortedSet(containers);
    }

    @NotNull
    @Override
    public SortedSet<WordContainer> getContainers() {
        return containers;
    }

    @NotNull
    @Override
    public BoardDimensions getDimensions() {
        int top = 0, left = 0, bottom = 0, right = 0;

        for (WordContainer container : getContainers()) {
            ContainerCoordinate startCoordinate = container.getStartCoordinate();
            ContainerCoordinate endCoordinate = container.getEndCoordinate();
            top = Math.min(top, startCoordinate.getTop());
            left = Math.min(left, startCoordinate.getLeft());
            bottom = Math.max(bottom, endCoordinate.getTop());
            right = Math.max(right, endCoordinate.getLeft());
        }

        return new BoardDimensionsImpl(right - left, bottom - top);
    }

    @NotNull
    private SortedSet<WordContainer> toSortedSet(@NotNull Collection<WordContainer> containers) {
        SortedSet<WordContainer> sortedContainers = new TreeSet<>();
        sortedContainers.addAll(containers);
        return Collections.unmodifiableSortedSet(sortedContainers);
    }
}
